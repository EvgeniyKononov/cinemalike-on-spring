package ru.astondevs.cinemalike_on_spring.genre.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ru.astondevs.cinemalike_on_spring.film.controller.dto.OutFilmDto;
import ru.astondevs.cinemalike_on_spring.film.controller.mapper.FilmDtoMapper;
import ru.astondevs.cinemalike_on_spring.film.model.Film;
import ru.astondevs.cinemalike_on_spring.film.service.FilmService;
import ru.astondevs.cinemalike_on_spring.genre.controller.dto.InGenreDto;
import ru.astondevs.cinemalike_on_spring.genre.controller.dto.OutGenreDto;
import ru.astondevs.cinemalike_on_spring.genre.controller.mapper.GenreDtoMapper;
import ru.astondevs.cinemalike_on_spring.genre.model.Genre;
import ru.astondevs.cinemalike_on_spring.genre.service.GenreService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class GenreControllerMockMvcTest {
    @Mock
    private GenreService genreService;
    @Mock
    private GenreDtoMapper genreDtoMapper;
    @Mock
    private FilmService filmService;
    @Mock
    private FilmDtoMapper filmDtoMapper;
    @InjectMocks
    private GenreController genreController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Long genreId;
    private Genre genre;
    private List<Film> films;
    private List<OutFilmDto> filmsDto;
    private OutGenreDto expected;
    private InGenreDto inGenreDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(genreController).build();
        objectMapper = new ObjectMapper();
        genreId = 1L;
        genre = new Genre();
        films = new ArrayList<>();
        filmsDto = new ArrayList<>();
        expected = new OutGenreDto();
        inGenreDto = new InGenreDto();
    }

    @Test
    void getGenreById_whenInvokedWithCorrectId_thenReturnDtoAndStatusOk() throws Exception {
        when(genreService.findById(genreId)).thenReturn(genre);
        when(filmService.findByGenreId(any())).thenReturn(films);
        when(filmDtoMapper.toOutDto(films)).thenReturn(filmsDto);
        when(genreDtoMapper.map(genre, filmsDto)).thenReturn(expected);

        String actual = mockMvc.perform(
                        get("/genre")
                                .param("id", String.valueOf(genreId)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expected), actual);
    }

    @Test
    void getGenreById_whenInvokedWithInCorrectId_thenStatusBadRequest() throws Exception {
        mockMvc.perform(
                        get("/genre")
                                .param("id", "Not Number"))
                .andExpect(status().isBadRequest());

        verify(genreService, never()).findById(genreId);
    }

    @Test
    void createGenreById_whenInvoked_thenReturnSavedGenreDtoAndStatusCreated() throws Exception {
        when(genreDtoMapper.toNewEntity(inGenreDto)).thenReturn(genre);
        when(genreService.save(genre)).thenReturn(genre);
        when(filmService.findByGenreId(any())).thenReturn(films);
        when(filmDtoMapper.toOutDto(films)).thenReturn(filmsDto);
        when(genreDtoMapper.map(genre, filmsDto)).thenReturn(expected);

        String actual = mockMvc.perform(
                        post("/genre")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(inGenreDto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expected), actual);
    }

    @Test
    void updateGenre_whenInvoked_thenReturnSavedGenreDtoAndStatusOk() throws Exception {
        Genre oldGenre = new Genre();
        when(genreService.findById(genreId)).thenReturn(oldGenre);
        when(genreDtoMapper.toNewEntity(inGenreDto)).thenReturn(genre);
        when(genreService.update(oldGenre, genre)).thenReturn(genre);
        when(filmService.findByGenreId(any())).thenReturn(films);
        when(filmDtoMapper.toOutDto(films)).thenReturn(filmsDto);
        when(genreDtoMapper.map(genre, filmsDto)).thenReturn(expected);

        String actual = mockMvc.perform(
                        patch("/genre")
                                .contentType("application/json")
                                .param("id", String.valueOf(genreId))
                                .content(objectMapper.writeValueAsString(inGenreDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expected), actual);
    }

    @Test
    void deleteGenre_whenInvokedWithCorrectId_thenStatusNoContent() throws Exception {
        mockMvc.perform(
                        delete("/genre")
                                .param("id", String.valueOf(genreId)))
                .andExpect(status().isNoContent());

        verify(genreService, only()).deleteById(genreId);
    }

    @Test
    void deleteGenre_whenInvokedWithIncorrectId_thenStatusBadRequest() throws Exception {
        mockMvc.perform(
                        delete("/genre")
                                .param("id", "Not Number"))
                .andExpect(status().isBadRequest());

        verify(genreService, never()).deleteById(genreId);
    }
}