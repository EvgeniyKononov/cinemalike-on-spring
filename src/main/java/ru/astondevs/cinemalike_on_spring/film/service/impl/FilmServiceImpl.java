package ru.astondevs.cinemalike_on_spring.film.service.impl;

import org.springframework.stereotype.Service;
import ru.astondevs.cinemalike_on_spring.exception.DuplicateException;
import ru.astondevs.cinemalike_on_spring.exception.NotFoundException;
import ru.astondevs.cinemalike_on_spring.film.model.Film;
import ru.astondevs.cinemalike_on_spring.film.repository.FilmRepository;
import ru.astondevs.cinemalike_on_spring.film.service.FilmService;

import java.util.List;
import java.util.Optional;

@Service
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;

    public FilmServiceImpl(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public Film findById(Long id) {
        return filmRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Film with such id not found"));
    }

    @Override
    public Film save(Film film) {
        Optional<Film> filmInDb = filmRepository.findByName(film.getName());
        if (filmInDb.isPresent()) {
            throw new DuplicateException("Film with such name already exist");
        }
        return filmRepository.save(film);
    }

    @Override
    public Film update(Film film, Film updatedFilm) {
        return filmRepository.save(new Film(film.getId(), updatedFilm.getName(), film.getUserLikes(),
                updatedFilm.getGenre()));
    }

    @Override
    public Film update(Film film) {
        return filmRepository.save(film);
    }

    @Override
    public void deleteById(Long id) {
        filmRepository.deleteById(id);
    }

    @Override
    public List<Film> findByGenreId(Long genreId) {
        return filmRepository.getFilmsByGenreId(genreId);
    }

}
