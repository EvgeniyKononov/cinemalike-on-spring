package ru.astondevs.cinemalike_on_spring.genre.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenreControllerTest {
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
    private Long genreId;
    private Genre genre;
    private List<Film> films;
    private List<OutFilmDto> filmsDto;
    private OutGenreDto expected;
    private OutGenreDto actual;
    private InGenreDto inGenreDto;

    @BeforeEach
    void setUp() {
        genreId = 1L;
        genre = new Genre();
        films = new ArrayList<>();
        filmsDto = new ArrayList<>();
        expected = new OutGenreDto();
        inGenreDto = new InGenreDto();
    }

    @Test
    void getGenreById_whenInvokedWithCorrectId_thenReturnDto() {
        when(genreService.findById(genreId)).thenReturn(genre);
        when(filmService.findByGenreId(any())).thenReturn(films);
        when(filmDtoMapper.toOutDto(films)).thenReturn(filmsDto);
        when(genreDtoMapper.map(genre, filmsDto)).thenReturn(expected);

        actual = genreController.getGenreById(genreId);

        assertEquals(expected, actual);
    }

    @Test
    void createGenre_whenInvoked_thenReturnSavedGenreDto() {
        when(genreDtoMapper.toNewEntity(inGenreDto)).thenReturn(genre);
        when(genreService.save(genre)).thenReturn(genre);
        when(filmService.findByGenreId(any())).thenReturn(films);
        when(filmDtoMapper.toOutDto(films)).thenReturn(filmsDto);
        when(genreDtoMapper.map(genre, filmsDto)).thenReturn(expected);

        actual = genreController.createGenre(inGenreDto);

        assertEquals(expected, actual);
    }

    @Test
    void updateGenre_whenUpdateGenre_thenReturnUpdatedGenreDto() {
        Genre oldGenre = new Genre();
        when(genreService.findById(genreId)).thenReturn(oldGenre);
        when(genreDtoMapper.toNewEntity(inGenreDto)).thenReturn(genre);
        when(genreService.update(oldGenre, genre)).thenReturn(genre);
        when(filmService.findByGenreId(any())).thenReturn(films);
        when(filmDtoMapper.toOutDto(films)).thenReturn(filmsDto);
        when(genreDtoMapper.map(genre, filmsDto)).thenReturn(expected);

        actual = genreController.updateGenre(genreId, inGenreDto);

        assertEquals(expected, actual);

    }

    @Test
    void deleteUser_whenInvoked_thenDeletingInServiceInvokedToo() {
        genreController.deleteUser(genreId);

        verify(genreService, only()).deleteById(genreId);
    }
}