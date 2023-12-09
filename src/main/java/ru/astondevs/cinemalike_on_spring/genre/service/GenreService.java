package ru.astondevs.cinemalike_on_spring.genre.service;

import ru.astondevs.cinemalike_on_spring.genre.model.Genre;

public interface GenreService {
    Genre findById(Long id);

    Genre findByName(String name);

    Genre save(Genre genre);

    Genre update(Genre genre, Genre updatedGenre);

    void deleteById(Long id);
}
