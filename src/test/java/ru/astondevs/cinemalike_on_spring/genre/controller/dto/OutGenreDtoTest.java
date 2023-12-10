package ru.astondevs.cinemalike_on_spring.genre.controller.dto;

import org.junit.jupiter.api.Test;
import ru.astondevs.cinemalike_on_spring.film.controller.dto.OutFilmDto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OutGenreDtoTest {
    @Test
    void settingMethods_whenCreateObjectsViaConstructorAndViaSetter_thenTheyAreEquals() {
        List<OutFilmDto> films = new ArrayList<>();
        OutGenreDto expected = new OutGenreDto("action", films);
        OutGenreDto actual = new OutGenreDto();
        actual.setName("action");
        actual.setFilms(films);

        assertEquals(expected, actual);
    }

}