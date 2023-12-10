package ru.astondevs.cinemalike_on_spring.like.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.cinemalike_on_spring.film.model.Film;
import ru.astondevs.cinemalike_on_spring.film.service.FilmService;
import ru.astondevs.cinemalike_on_spring.film.service.impl.FilmServiceImpl;
import ru.astondevs.cinemalike_on_spring.like.service.LikeService;
import ru.astondevs.cinemalike_on_spring.user.model.User;
import ru.astondevs.cinemalike_on_spring.user.service.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LikeServiceImplTest {
    @Mock
    private UserService userService;
    @Mock
    private FilmServiceImpl filmService;
    @InjectMocks
    private LikeServiceImpl likeService;
    private Long userId;
    private Long filmId;
    private User user;
    private Film film;

    @BeforeEach
    void setUp() {
        userId = 1L;
        filmId = 1L;
        user = new User();
        film = new Film();
    }

    @Test
    void setLike_whenInvoked_thenFilmServiceUpdateFilm() {
        when(userService.findById(userId)).thenReturn(user);
        when(filmService.findById(userId)).thenReturn(film);
        when(filmService.update(film)).thenReturn(film);

        likeService.setLike(userId, filmId);

       verifyNoMoreInteractions(filmService);
    }

    @Test
    void deleteLike_whenInvoked_thenFilmServiceUpdateFilm() {
        when(userService.findById(userId)).thenReturn(user);
        when(filmService.findById(userId)).thenReturn(film);
        when(filmService.update(film)).thenReturn(film);

        likeService.deleteLike(userId, filmId);

        verifyNoMoreInteractions(filmService);
    }
}