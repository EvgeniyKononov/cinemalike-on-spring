package ru.astondevs.cinemalike_on_spring.genre.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.astondevs.cinemalike_on_spring.genre.model.Genre;

import java.util.Optional;

@Repository
public interface GenreRepository extends CrudRepository<Genre, Long> {
    Optional<Genre> findByName(String name);

}
