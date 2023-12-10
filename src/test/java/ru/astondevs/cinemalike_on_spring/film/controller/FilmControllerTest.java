package ru.astondevs.cinemalike_on_spring.film.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.cinemalike_on_spring.film.controller.dto.InFilmDto;
import ru.astondevs.cinemalike_on_spring.film.controller.dto.OutFilmDto;
import ru.astondevs.cinemalike_on_spring.film.controller.mapper.FilmDtoMapper;
import ru.astondevs.cinemalike_on_spring.film.model.Film;
import ru.astondevs.cinemalike_on_spring.film.service.FilmService;
import ru.astondevs.cinemalike_on_spring.genre.controller.dto.InGenreDto;
import ru.astondevs.cinemalike_on_spring.genre.model.Genre;
import ru.astondevs.cinemalike_on_spring.genre.service.GenreService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FilmControllerTest {
    @Mock
    private FilmService filmService;
    @Mock
    private GenreService genreService;
    @Mock
    private FilmDtoMapper filmDtoMapper;
    @InjectMocks
    private FilmController filmController;
    private Film film;
    private Long filmId;
    private OutFilmDto expected;
    private OutFilmDto actual;
    private InFilmDto inFilmDto;
    private Genre genre;
    private String genreName;

    @BeforeEach
    void setUp() {
        filmId = 1L;
        film = new Film();
        expected = new OutFilmDto();
        genreName = "genre";
        genre = new Genre(genreName);
        inFilmDto = new InFilmDto("film", new InGenreDto(genreName));
    }

    @Test
    void getFilmById_whenInvokedWithCorrectId_thenReturnDto() {
        when(filmService.findById(filmId)).thenReturn(film);
        when(filmDtoMapper.map(film)).thenReturn(expected);

        actual = filmController.getFilmById(filmId);

        assertEquals(expected, actual);
    }

    @Test
    void createFilm_whenInvoked_thenReturnSavedFilmDto() {
        when(genreService.findByName(genreName)).thenReturn(genre);
        when(filmDtoMapper.toNewEntity(inFilmDto, genre)).thenReturn(film);
        when(filmService.save(film)).thenReturn(film);
        when(filmDtoMapper.map(film)).thenReturn(expected);

        actual = filmController.createFilm(inFilmDto);

        assertEquals(expected, actual);
    }

    @Test
    void updateFilm_whenUpdateFilm_thenReturnUpdatedFilmDto() {
        Film oldFilm = new Film();
        when(filmService.findById(filmId)).thenReturn(oldFilm);
        when(genreService.findByName(genreName)).thenReturn(genre);
        when(filmDtoMapper.toNewEntity(inFilmDto, genre)).thenReturn(film);
        when(filmService.update(oldFilm, film)).thenReturn(film);
        when(filmDtoMapper.map(film)).thenReturn(expected);

        actual = filmController.updateFilm(filmId, inFilmDto);

        assertEquals(expected, actual);
    }

    @Test
    void deleteFilm_whenInvoked_thenDeletingInServiceInvokedToo() {
        filmController.deleteFilm(filmId);

        verify(filmService, only()).deleteById(filmId);
    }
}