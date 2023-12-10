package ru.astondevs.cinemalike_on_spring.user.controller.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InUserDtoTest {

    @Test
    void inUserDtoSettingMethods_whenCreateObjectsViaConstructorAndViaSetter_thenTheyAreEquals() {
        InUserDto expected = new InUserDto("Garik", "Igor");
        InUserDto actual = new InUserDto();

        actual.setLogin("Garik");
        actual.setName("Igor");

        assertEquals(expected, actual);
    }
}