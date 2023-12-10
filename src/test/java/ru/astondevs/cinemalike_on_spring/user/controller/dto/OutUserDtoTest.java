package ru.astondevs.cinemalike_on_spring.user.controller.dto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OutUserDtoTest {

    @Test
    void outUserDtoSettingMethods_whenCreateObjectsViaConstructorAndViaSetter_thenTheyAreEquals() {
        OutUserDto expected = new OutUserDto(1L, "Garik", new ArrayList<>());
        OutUserDto actual = new OutUserDto();

        actual.setId(1L);
        actual.setName("Garik");
        actual.setFilmLikes(new ArrayList<>());

        assertAll("Assertion fields",
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getName(), actual.getName()),
                () -> assertEquals(expected.getFilmLikes(), actual.getFilmLikes()),
                () -> assertEquals(expected, actual));
    }
}