package ru.astondevs.cinemalike_on_spring.genre.controller.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.cinemalike_on_spring.film.controller.dto.InFilmDto;
import ru.astondevs.cinemalike_on_spring.film.controller.dto.OutFilmDto;
import ru.astondevs.cinemalike_on_spring.film.controller.mapper.FilmDtoMapper;
import ru.astondevs.cinemalike_on_spring.film.model.Film;
import ru.astondevs.cinemalike_on_spring.genre.controller.dto.InGenreDto;
import ru.astondevs.cinemalike_on_spring.genre.controller.dto.OutGenreDto;
import ru.astondevs.cinemalike_on_spring.genre.model.Genre;
import ru.astondevs.cinemalike_on_spring.user.controller.dto.OutUserDto;
import ru.astondevs.cinemalike_on_spring.user.model.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenreDtoMapperImplTest {
    @Mock
    private FilmDtoMapper filmDtoMapper;
    @InjectMocks
    private GenreDtoMapperImpl mapper = new GenreDtoMapperImpl();
    private Genre genre;
    private List<OutFilmDto> films;
    private OutGenreDto outGenreDto;
    private InGenreDto inGenreDto;

    @BeforeEach
    void setUp() {
        genre = new Genre(1L, "action");
        OutUserDto outUserDto = new OutUserDto(1L, "Igor", new ArrayList<>());
        OutFilmDto outFilmDto = new OutFilmDto("Terminator", List.of(outUserDto), "action");
        films = List.of(outFilmDto);
        outGenreDto = new OutGenreDto("action", films);
        inGenreDto = new InGenreDto("action");
    }

    @Test
    void map_whenInvokedWithGenreAndFilms_thenReturnDto() {
        when(filmDtoMapper.toOutDto(any())).thenReturn(films);

        OutGenreDto actual = mapper.map(genre, films);

        assertEquals(outGenreDto, actual);
    }

    @Test
    void map_whenInvokedWithNullGenreAndFilms_thenReturnNull() {
        genre = null;
        films = null;

        OutGenreDto actual = mapper.map(genre, films);

        assertNull(actual);
    }

    @Test
    void map_whenInvokedWithGenreNameAndFilms_thenReturnDto() {
        OutGenreDto actual = mapper.map(genre.getName(), films);

        assertEquals(outGenreDto, actual);
    }

    @Test
    void map_whenInvokedWithNullGenreNameAndFilms_thenReturnNull() {
        genre.setName(null);
        films = null;

        OutGenreDto actual = mapper.map(genre.getName(), films);

        assertNull(actual);
    }

    @Test
    void toNewEntity_whenInvokeWIthDto_thenReturnGenre() {
        genre.setId(null);
        genre.setFilms(null);

        Genre actual = mapper.toNewEntity(inGenreDto);

        assertEquals(genre, actual);
    }

    @Test
    void toNewEntity_whenInvokeWIthNullDto_thenReturnNull() {
        inGenreDto = null;

        Genre actual = mapper.toNewEntity(inGenreDto);

        assertNull(actual);
    }

    @Test
    void toEntity_whenInvokeWIthDto_thenReturnGenre() {
        genre.setId(null);
        genre.setFilms(null);

        Genre actual = mapper.toEntity(inGenreDto);

        assertEquals(genre, actual);
    }

    @Test
    void toEntity_whenInvokeWIthNullDto_thenReturnNull() {
        inGenreDto = null;

        Genre actual = mapper.toEntity(inGenreDto);

        assertNull(actual);
    }
}