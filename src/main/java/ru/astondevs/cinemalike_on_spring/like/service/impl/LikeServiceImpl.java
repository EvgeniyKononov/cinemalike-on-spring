package ru.astondevs.cinemalike_on_spring.like.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.astondevs.cinemalike_on_spring.film.model.Film;
import ru.astondevs.cinemalike_on_spring.film.service.FilmService;
import ru.astondevs.cinemalike_on_spring.like.service.LikeService;
import ru.astondevs.cinemalike_on_spring.user.model.User;
import ru.astondevs.cinemalike_on_spring.user.service.UserService;

import java.util.List;

@Service
public class LikeServiceImpl implements LikeService {
    private final UserService userService;
    private final FilmService filmService;

    @Autowired
    public LikeServiceImpl(UserService userService, FilmService filmService) {
        this.userService = userService;
        this.filmService = filmService;
    }

    @Override
    public void setLike(Long userId, Long filmId) {
        User user = userService.findById(userId);
        Film film = filmService.findById(filmId);
        List<User> users = film.getUserLikes();
        users.add(user);
        film.setUserLikes(users);
        filmService.update(film);
    }

    @Override
    public void deleteLike(Long userId, Long filmId) {
        User user = userService.findById(userId);
        Film film = filmService.findById(filmId);
        List<User> users = film.getUserLikes();
        users.remove(user);
        film.setUserLikes(users);
        filmService.update(film);
    }
}
