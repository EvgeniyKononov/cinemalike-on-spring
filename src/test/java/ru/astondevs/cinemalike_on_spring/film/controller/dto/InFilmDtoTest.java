package ru.astondevs.cinemalike_on_spring.film.controller.dto;

import org.junit.jupiter.api.Test;
import ru.astondevs.cinemalike_on_spring.genre.controller.dto.InGenreDto;

import static org.junit.jupiter.api.Assertions.*;

class InFilmDtoTest {

    @Test
    void inFilmDtoSettingMethods_whenCreateObjectsViaConstructorAndViaSetter_thenTheyAreEquals() {
        InFilmDto expected = new InFilmDto("Terminator", new InGenreDto("action"));
        InFilmDto actual = new InFilmDto();

        actual.setName("Terminator");
        actual.setGenreDto(new InGenreDto("action"));

        assertEquals(expected, actual);
    }
}