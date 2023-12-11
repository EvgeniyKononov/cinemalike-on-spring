package ru.astondevs.cinemalike_on_spring.film.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ru.astondevs.cinemalike_on_spring.film.controller.dto.InFilmDto;
import ru.astondevs.cinemalike_on_spring.film.controller.dto.OutFilmDto;
import ru.astondevs.cinemalike_on_spring.film.controller.mapper.FilmDtoMapper;
import ru.astondevs.cinemalike_on_spring.film.model.Film;
import ru.astondevs.cinemalike_on_spring.film.service.FilmService;
import ru.astondevs.cinemalike_on_spring.genre.controller.dto.InGenreDto;
import ru.astondevs.cinemalike_on_spring.genre.model.Genre;
import ru.astondevs.cinemalike_on_spring.genre.service.GenreService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class FilmControllerMockMvcTest {
    @Mock
    private FilmService filmService;
    @Mock
    private GenreService genreService;
    @Mock
    private FilmDtoMapper filmDtoMapper;
    @InjectMocks
    private FilmController filmController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Film film;
    private Long filmId;
    private OutFilmDto expected;
    private InFilmDto inFilmDto;
    private Genre genre;
    private String genreName;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(filmController).build();
        objectMapper = new ObjectMapper();
        filmId = 1L;
        film = new Film();
        expected = new OutFilmDto();
        genreName = "genre";
        genre = new Genre(genreName);
        inFilmDto = new InFilmDto("film", new InGenreDto(genreName));
    }

    @Test
    void getFilmById_whenInvokedWithCorrectId_thenReturnDtoAndStatusOk() throws Exception {
        when(filmService.findById(filmId)).thenReturn(film);
        when(filmDtoMapper.map(film)).thenReturn(expected);

        String actual = mockMvc.perform(
                        get("/film")
                                .param("id", String.valueOf(filmId)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expected), actual);
    }

    @Test
    void getFilmById_whenInvokedWithInCorrectId_thenStatusBadRequest() throws Exception {
        mockMvc.perform(
                        get("/film")
                                .param("id", "Not Number"))
                .andExpect(status().isBadRequest());

        verify(filmService, never()).findById(filmId);
    }

    @Test
    void createFilm_whenInvoked_thenReturnSavedFilmDtoAndStatusCreated() throws Exception {
        when(genreService.findByName(genreName)).thenReturn(genre);
        when(filmDtoMapper.toNewEntity(inFilmDto, genre)).thenReturn(film);
        when(filmService.save(film)).thenReturn(film);
        when(filmDtoMapper.map(film)).thenReturn(expected);

        String actual = mockMvc.perform(
                        post("/film")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(inFilmDto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expected), actual);
    }

    @Test
    void updateFilm_whenInvoked_thenReturnSavedFilmDtoAndStatusOk() throws Exception {
        Film oldFilm = new Film();
        when(filmService.findById(filmId)).thenReturn(oldFilm);
        when(genreService.findByName(genreName)).thenReturn(genre);
        when(filmDtoMapper.toNewEntity(inFilmDto, genre)).thenReturn(film);
        when(filmService.update(oldFilm, film)).thenReturn(film);
        when(filmDtoMapper.map(film)).thenReturn(expected);

        String actual = mockMvc.perform(
                        patch("/film")
                                .contentType("application/json")
                                .param("id", String.valueOf(filmId))
                                .content(objectMapper.writeValueAsString(inFilmDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expected), actual);
    }

    @Test
    void deleteFilm_whenInvokedWithCorrectId_thenStatusNoContent() throws Exception {
        mockMvc.perform(
                        delete("/film")
                                .param("id", String.valueOf(filmId)))
                .andExpect(status().isNoContent());

        verify(filmService, only()).deleteById(filmId);
    }

    @Test
    void deleteFilm_whenInvokedWithIncorrectId_thenStatusBadRequest() throws Exception {
        mockMvc.perform(
                        delete("/film")
                                .param("id", "Not Number"))
                .andExpect(status().isBadRequest());

        verify(filmService, never()).deleteById(filmId);
    }
}