package ru.astondevs.cinemalike_on_spring.genre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.astondevs.cinemalike_on_spring.film.controller.dto.OutFilmDto;
import ru.astondevs.cinemalike_on_spring.film.controller.mapper.FilmDtoMapper;
import ru.astondevs.cinemalike_on_spring.film.model.Film;
import ru.astondevs.cinemalike_on_spring.film.service.FilmService;
import ru.astondevs.cinemalike_on_spring.genre.controller.dto.InGenreDto;
import ru.astondevs.cinemalike_on_spring.genre.controller.dto.OutGenreDto;
import ru.astondevs.cinemalike_on_spring.genre.controller.mapper.GenreDtoMapper;
import ru.astondevs.cinemalike_on_spring.genre.model.Genre;
import ru.astondevs.cinemalike_on_spring.genre.service.GenreService;

import java.util.List;

@RestController
@RequestMapping(path = "/genre")
public class GenreController {
    private final GenreService genreService;
    private final GenreDtoMapper dtoMapper;
    private final FilmService filmService;
    private final FilmDtoMapper filmDtoMapper;

    @Autowired
    public GenreController(GenreService genreService, GenreDtoMapper dtoMapper,
                           FilmService filmService, FilmDtoMapper filmDtoMapper) {
        this.genreService = genreService;
        this.dtoMapper = dtoMapper;
        this.filmService = filmService;
        this.filmDtoMapper = filmDtoMapper;
    }

    @GetMapping
    public OutGenreDto getGenreByIdy(@RequestParam long id) {
        Genre genre = genreService.findById(id);
        return dtoMapper.map(genre, getOutFilmsDtoByGenreId(genre.getId()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OutGenreDto createGenre(@RequestBody InGenreDto inGenreDto) {
        Genre genre = dtoMapper.toNewEntity(inGenreDto);
        genre = genreService.save(genre);
        return dtoMapper.map(genre, getOutFilmsDtoByGenreId(genre.getId()));
    }

    @PatchMapping
    public OutGenreDto createGenre(@RequestParam long id, @RequestBody InGenreDto inGenreDto) {
        Genre oldGenre = genreService.findById(id);
        Genre newGenre = dtoMapper.toNewEntity(inGenreDto);
        newGenre = genreService.update(oldGenre, newGenre);
        return dtoMapper.map(newGenre, getOutFilmsDtoByGenreId(newGenre.getId()));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@RequestParam long id) {
        genreService.deleteById(id);
    }

    private List<OutFilmDto> getOutFilmsDtoByGenreId(Long genreId) {
        List<Film> films = filmService.findByGenreId(genreId);
        return filmDtoMapper.toOutDto(films);
    }
}
