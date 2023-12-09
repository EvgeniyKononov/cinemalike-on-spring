package ru.astondevs.cinemalike_on_spring.film.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.astondevs.cinemalike_on_spring.film.controller.dto.InFilmDto;
import ru.astondevs.cinemalike_on_spring.film.controller.dto.OutFilmDto;
import ru.astondevs.cinemalike_on_spring.film.model.Film;
import ru.astondevs.cinemalike_on_spring.genre.model.Genre;
import ru.astondevs.cinemalike_on_spring.user.controller.mapper.UserDtoMapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserDtoMapper.class})
public interface FilmDtoMapper {
    @Mapping(source = "film.name", target = "name")
    @Mapping(source = "genre.name", target = "genre")
    @Mapping(source = "film.userLikes", target = "userLikes")
    OutFilmDto map(Film film, Genre genre);
    @Mapping(source = "film.genre.name", target = "genre")
    OutFilmDto map(Film film);
    @Mapping(target = "id", constant = "-1L")
    @Mapping(source = "inFilmDto.name", target = "name")
    @Mapping(source = "genre", target = "genre")
    Film toNewEntity(InFilmDto inFilmDto, Genre genre);

    List<OutFilmDto> toOutDto(List<Film> films);
}
