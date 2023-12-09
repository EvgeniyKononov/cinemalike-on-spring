package ru.astondevs.cinemalike_on_spring.genre.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.astondevs.cinemalike_on_spring.exception.DuplicateException;
import ru.astondevs.cinemalike_on_spring.exception.NotFoundException;
import ru.astondevs.cinemalike_on_spring.genre.model.Genre;
import ru.astondevs.cinemalike_on_spring.genre.repository.GenreRepository;
import ru.astondevs.cinemalike_on_spring.genre.service.GenreService;

import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public Genre findById(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Genre with such id not found"));
    }

    @Override
    public Genre findByName(String name) {
        return genreRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Genre with such name not found"));
    }

    @Override
    public Genre save(Genre genre) {
        Optional<Genre> genreInDb = genreRepository.findByName(genre.getName());
        if (genreInDb.isPresent()) {
            throw new DuplicateException("Genre with such name already exist");
        }
        return genreRepository.save(genre);
    }

    @Override
    public Genre update(Genre genre, Genre updatedGenre) {
        return genreRepository.save(new Genre(genre.getId(), updatedGenre.getName()));
    }

    @Override
    public void deleteById(Long id) {
        genreRepository.deleteById(id);
    }
}
