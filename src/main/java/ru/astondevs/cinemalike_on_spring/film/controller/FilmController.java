package ru.astondevs.cinemalike_on_spring.film.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.astondevs.cinemalike_on_spring.film.controller.dto.InFilmDto;
import ru.astondevs.cinemalike_on_spring.film.controller.dto.OutFilmDto;
import ru.astondevs.cinemalike_on_spring.film.controller.mapper.FilmDtoMapper;
import ru.astondevs.cinemalike_on_spring.film.model.Film;
import ru.astondevs.cinemalike_on_spring.film.service.FilmService;
import ru.astondevs.cinemalike_on_spring.genre.model.Genre;
import ru.astondevs.cinemalike_on_spring.genre.service.GenreService;

@RestController
@RequestMapping(path = "/film")
public class FilmController {
    private final FilmService filmService;
    private final GenreService genreService;
    private final FilmDtoMapper dtoMapper;

    @Autowired
    public FilmController(FilmService filmService, GenreService genreService, FilmDtoMapper dtoMapper) {
        this.filmService = filmService;
        this.genreService = genreService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping
    public OutFilmDto getFilmById(@RequestParam long id) {
        Film film = filmService.findById(id);
        return dtoMapper.map(film);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OutFilmDto createFilm(@RequestBody InFilmDto inFilmDto){
        Genre genre = genreService.findByName(inFilmDto.getGenreDto().getName());
        Film film = dtoMapper.toNewEntity(inFilmDto, genre);
        film = filmService.save(film);
        return dtoMapper.map(film);
    }

    @PatchMapping
    public OutFilmDto updateFilm(@RequestParam long id, @RequestBody InFilmDto inFilmDto){
        Film oldFilm = filmService.findById(id);
        Genre genre = genreService.findByName(inFilmDto.getGenreDto().getName());
        Film newFilm = dtoMapper.toNewEntity(inFilmDto, genre);
        newFilm = filmService.update(oldFilm, newFilm);
        return dtoMapper.map(newFilm);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@RequestParam long id) {
        filmService.deleteById(id);
    }
}
