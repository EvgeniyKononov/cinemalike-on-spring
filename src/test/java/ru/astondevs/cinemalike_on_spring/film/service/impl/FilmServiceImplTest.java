package ru.astondevs.cinemalike_on_spring.film.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.cinemalike_on_spring.exception.DuplicateException;
import ru.astondevs.cinemalike_on_spring.exception.NotFoundException;
import ru.astondevs.cinemalike_on_spring.film.model.Film;
import ru.astondevs.cinemalike_on_spring.film.repository.FilmRepository;
import ru.astondevs.cinemalike_on_spring.genre.model.Genre;
import ru.astondevs.cinemalike_on_spring.user.model.User;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FilmServiceImplTest {
    @Mock
    private FilmRepository repository;
    @InjectMocks
    private FilmServiceImpl service;
    private Film expected;
    private Film actual;
    private Long filmId;
    private String filmName;
    private Long genreId;
    private List<Film> films;

    @BeforeEach
    void setUp() {
        filmId = 1L;
        genreId = 3L;
        filmName = "Terminator";
        Genre genre = new Genre(genreId, "action");
        List<User> users = new ArrayList<>(List.of(new User("sherminator", "sherman")));
        films = new ArrayList<>();
        expected = new Film(filmId, filmName, users, genre);
    }

    @Test
    void findById_whenFilmFound_thenReturnFilm() {
        when(repository.findById(filmId)).thenReturn(Optional.ofNullable(expected));

        actual = service.findById(filmId);

        assertEquals(expected, actual);
    }

    @Test
    void findById_whenFilmNotFound_thenThrowNotFoundException() {
        when(repository.findById(filmId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.findById(filmId));
    }

    @Test
    void save_whenFilmSaved_thenReturnFilm() {
        when(repository.findByName(filmName)).thenReturn(Optional.empty());
        when(repository.save(expected)).thenReturn(expected);

        actual = service.save(expected);

        assertEquals(expected, actual);
    }

    @Test
    void save_whenFilmAlreadyExist_thenThrowDuplicateException() {
        when(repository.findByName(filmName)).thenReturn(Optional.ofNullable(expected));

        assertThrows(DuplicateException.class, () -> service.save(expected));

        verify(repository, never()).save(any());
    }

    @Test
    void update_whenFilmUpdated_thenReturnFilm() {
        when(repository.save(expected)).thenReturn(expected);

        actual = service.update(expected, expected);

        assertEquals(expected, actual);
    }

    @Test
    void deleteById_whenDeleteFilm_thenInvokeDeletingFromRepository() {
        service.deleteById(filmId);

        verify(repository, only()).deleteById(filmId);
    }

    @Test
    void findByGenreId_whenFilmsFound_thenReturnFilms() {
        when(repository.getFilmsByGenreId(genreId)).thenReturn(films);

        List<Film> actual = service.findByGenreId(genreId);

        assertEquals(films, actual);
    }
}