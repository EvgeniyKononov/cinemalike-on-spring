package ru.astondevs.cinemalike_on_spring.film.service;

import ru.astondevs.cinemalike_on_spring.film.model.Film;

import java.util.List;

public interface FilmService {
    Film findById(Long id);

    Film save(Film film);

    Film update(Film film, Film updatedFilm);
    Film update(Film film);

    void deleteById(Long id);

    List<Film> findByGenreId(Long genreId);
}
