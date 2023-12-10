package ru.astondevs.cinemalike_on_spring.film.controller.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import ru.astondevs.cinemalike_on_spring.film.controller.dto.InFilmDto;
import ru.astondevs.cinemalike_on_spring.film.controller.dto.OutFilmDto;
import ru.astondevs.cinemalike_on_spring.film.model.Film;
import ru.astondevs.cinemalike_on_spring.genre.controller.dto.InGenreDto;
import ru.astondevs.cinemalike_on_spring.genre.model.Genre;
import ru.astondevs.cinemalike_on_spring.user.controller.dto.OutUserDto;
import ru.astondevs.cinemalike_on_spring.user.controller.mapper.UserDtoMapper;
import ru.astondevs.cinemalike_on_spring.user.model.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FilmDtoMapperImplTest {
    @Spy
    private UserDtoMapper userDtoMapper;
    @InjectMocks
    private FilmDtoMapperImpl mapper = new FilmDtoMapperImpl();
    private Film film;
    private Genre genre;
    private OutFilmDto outFilmDto;
    private User user;
    private OutUserDto outUserDto;
    private InFilmDto inFilmDto;

    @BeforeEach
    void setUp() {
        genre = new Genre(1L, "action");
        user = new User(1L, "Garik", "Igor");
        outUserDto = new OutUserDto(1L, "Igor", new ArrayList<>());
        film = new Film(1L, "Terminator", List.of(user), genre);
        outFilmDto = new OutFilmDto("Terminator", List.of(outUserDto), "action");
        inFilmDto = new InFilmDto("Terminator", new InGenreDto("action"));
    }

    @Test
    void map_whenInvokeWithFilmAndGenre_thenReturnDto() {
        OutFilmDto actual = mapper.map(film, genre);

        assertEquals(outFilmDto, actual);
    }

    @Test
    void map_whenInvokeWithNull_thenReturnNull() {
        film = null;
        genre = null;

        OutFilmDto actual = mapper.map(film, genre);

        assertNull(actual);
    }

    @Test
    void map_whenInvokeWithFilm_thenReturnDto() {
        OutFilmDto actual = mapper.map(film);

        assertEquals(outFilmDto, actual);
    }

    @Test
    void map_whenInvokeWithNullFilm_thenReturnNull() {
        film = null;

        OutFilmDto actual = mapper.map(film);

        assertNull(actual);
    }

    @Test
    void toNewEntity_whenInvokeWithDtoAndGenre_thenReturnFilmWithDefaultId() {
        film.setId(-1L);
        film.setUserLikes(new ArrayList<>());

        Film actual = mapper.toNewEntity(inFilmDto, genre);

        assertEquals(film, actual);
    }

    @Test
    void toNewEntity_whenInvokeWithNullDtoAndGenre_thenReturnNull() {
        inFilmDto = null;
        genre = null;

        Film actual = mapper.toNewEntity(inFilmDto, genre);

        assertNull(actual);
    }

    @Test
    void toOutDto_whenInvokeWithFilmsList_thenReturnDto() {
        List<Film> films = List.of(film);
        List<OutFilmDto> expected = List.of(outFilmDto);

        List<OutFilmDto> actual = mapper.toOutDto(films);

        assertEquals(expected, actual);
    }

    @Test
    void toOutDto_whenInvokeWithNullFilmsList_thenReturnNull() {
        List<Film> films = null;

        List<OutFilmDto> actual = mapper.toOutDto(films);

        assertNull(actual);
    }
}