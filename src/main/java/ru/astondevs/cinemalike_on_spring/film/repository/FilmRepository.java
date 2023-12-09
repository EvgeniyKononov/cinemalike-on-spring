package ru.astondevs.cinemalike_on_spring.film.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.astondevs.cinemalike_on_spring.film.model.Film;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmRepository extends CrudRepository<Film, Long> {
    Optional<Film> findByName(String name);

    List<Film> getFilmsByGenreId(Long id);
}
