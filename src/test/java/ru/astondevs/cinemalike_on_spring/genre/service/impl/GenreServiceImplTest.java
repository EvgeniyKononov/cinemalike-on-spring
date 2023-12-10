package ru.astondevs.cinemalike_on_spring.genre.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.cinemalike_on_spring.exception.DuplicateException;
import ru.astondevs.cinemalike_on_spring.exception.NotFoundException;
import ru.astondevs.cinemalike_on_spring.genre.model.Genre;
import ru.astondevs.cinemalike_on_spring.genre.repository.GenreRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenreServiceImplTest {
    @Mock
    private GenreRepository repository;
    @InjectMocks
    private GenreServiceImpl service;
    private Genre expected;
    private Genre actual;
    private Long id;
    private String name;


    @BeforeEach
    void setUp() {
        id = 1L;
        name = "action";
        expected = new Genre(id, name);
    }

    @Test
    void findById_whenGenreFound_thenReturnGenre() {
        when(repository.findById(id)).thenReturn(Optional.ofNullable(expected));

        actual = service.findById(id);

        assertEquals(expected, actual);
    }

    @Test
    void findById_whenGenreNotFound_thenThrowNotFoundException() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> service.findById(id));
    }

    @Test
    void findByName_whenGenreFound_thenReturnGenre() {
        when(repository.findByName(name)).thenReturn(Optional.ofNullable(expected));

        actual = service.findByName(name);

        assertEquals(expected, actual);
    }

    @Test
    void findByName_whenGenreNotFound_thenThrowNotFoundException() {
        when(repository.findByName(name)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> service.findByName(name));
    }

    @Test
    void save_whenGenreSaved_thenReturnGenre() {
        when(repository.findByName(name)).thenReturn(Optional.empty());
        when(repository.save(expected)).thenReturn(expected);

        actual = service.save(expected);

        assertEquals(expected, actual);
    }

    @Test
    void save_whenGenreExist_thenThrowDuplicateException() {
        when(repository.findByName(name)).thenReturn(Optional.ofNullable(expected));

        assertThrows(DuplicateException.class,
                () -> service.save(expected));

        verify(repository, never()).save(expected);
    }

    @Test
    void update_whenUpdateGenre_thenReturnUpdatedGenre() {
        when(repository.save(any())).thenReturn(expected);

        actual = service.update(expected, new Genre());

        assertEquals(expected, actual);
    }

    @Test
    void deleteById_whenDeleteGenre_thenReturnTrueIfSuccess() {
        service.deleteById(id);

        verify(repository, only()).deleteById(id);
    }
}