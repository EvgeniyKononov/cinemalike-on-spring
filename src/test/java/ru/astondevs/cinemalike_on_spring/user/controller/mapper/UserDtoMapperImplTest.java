package ru.astondevs.cinemalike_on_spring.user.controller.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.astondevs.cinemalike_on_spring.user.controller.dto.InUserDto;
import ru.astondevs.cinemalike_on_spring.user.controller.dto.OutUserDto;
import ru.astondevs.cinemalike_on_spring.user.model.User;

import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoMapperImplTest {
    private UserDtoMapperImpl mapper;
    private User user;
    private InUserDto inUserDto;
    private OutUserDto outUserDto;

    @BeforeEach
    void setUp() {
        mapper = new UserDtoMapperImpl();
        user = new User("login", "name");
        inUserDto = new InUserDto("login", "name");
        outUserDto = new OutUserDto(1L, "name", new ArrayList<>());
    }

    @Test
    void map_whenInvokeWithDto_thenReturnUser() {
        User actual = mapper.map(inUserDto);

        assertEquals(user, actual);
    }

    @Test
    void map_whenInvokeWithNull_thenReturnNull() {
        inUserDto = null;

        User actual = mapper.map(inUserDto);

        assertNull(actual);
    }

    @Test
    void map_whenInvokeWithSetUsers_thenReturnDtoSet() {
        Set<OutUserDto> expected = Set.of(outUserDto);
        user.setId(1L);
        Set<User> users = Set.of(user);

        Set<OutUserDto> actual = mapper.map(users);

        assertEquals(expected, actual);
    }

    @Test
    void map_whenInvokeWithNullSet_thenReturnNull() {
        Set<User> users = null;

        Set<OutUserDto> actual = mapper.map(users);

        assertNull(actual);
    }
}