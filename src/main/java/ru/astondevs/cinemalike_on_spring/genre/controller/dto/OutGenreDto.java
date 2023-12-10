package ru.astondevs.cinemalike_on_spring.genre.controller.dto;

import ru.astondevs.cinemalike_on_spring.film.controller.dto.OutFilmDto;

import java.util.List;
import java.util.Objects;

public class OutGenreDto {
    private String name;
    private List<OutFilmDto> films;

    public OutGenreDto() {
    }

    public OutGenreDto(String name) {
        this.name = name;
    }

    public OutGenreDto(String name, List<OutFilmDto> films) {
        this.name = name;
        this.films = films;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OutFilmDto> getFilms() {
        return films;
    }

    public void setFilms(List<OutFilmDto> films) {
        this.films = films;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OutGenreDto genreDto = (OutGenreDto) o;
        return Objects.equals(name, genreDto.name) && Objects.equals(films, genreDto.films);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, films);
    }

}
