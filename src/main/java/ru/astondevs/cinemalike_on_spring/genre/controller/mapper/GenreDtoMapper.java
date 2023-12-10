package ru.astondevs.cinemalike_on_spring.genre.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.astondevs.cinemalike_on_spring.film.controller.dto.OutFilmDto;
import ru.astondevs.cinemalike_on_spring.film.controller.mapper.FilmDtoMapper;
import ru.astondevs.cinemalike_on_spring.genre.controller.dto.InGenreDto;
import ru.astondevs.cinemalike_on_spring.genre.controller.dto.OutGenreDto;
import ru.astondevs.cinemalike_on_spring.genre.model.Genre;

import java.util.List;

@Mapper(componentModel = "spring", uses = {FilmDtoMapper.class})
public interface GenreDtoMapper {
    @Mapping(source = "genre.name", target = "name")
    OutGenreDto map(Genre genre, List<OutFilmDto> films);

    @Mapping(source = "genreName", target = "name")
    OutGenreDto map(String genreName, List<OutFilmDto> films);

    Genre toNewEntity(InGenreDto dto);

    Genre toEntity(InGenreDto dto);


}
