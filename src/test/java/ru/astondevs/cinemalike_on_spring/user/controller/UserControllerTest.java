package ru.astondevs.cinemalike_on_spring.user.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.cinemalike_on_spring.user.controller.dto.InUserDto;
import ru.astondevs.cinemalike_on_spring.user.controller.dto.OutUserDto;
import ru.astondevs.cinemalike_on_spring.user.controller.mapper.UserDtoMapper;
import ru.astondevs.cinemalike_on_spring.user.model.User;
import ru.astondevs.cinemalike_on_spring.user.service.UserService;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    private UserService userService;
    @Spy
    private UserDtoMapper dtoMapper;
    @InjectMocks
    private UserController userController;
    private Long userId;
    private OutUserDto expected;
    private OutUserDto actual;
    private User user;
    private InUserDto inUserDto;

    @BeforeEach
    void setUp() {
        userId = 1L;
        expected = new OutUserDto(1L, "name", new ArrayList<>());
        user = new User(1L,"login", "name");
        inUserDto = new InUserDto();
    }

    @Test
    void getUserById_whenInvokedWithCorrectId_thenReturnDto() {
        when(userService.findById(userId)).thenReturn(user);

        actual = userController.getUserById(userId);

        assertEquals(expected, actual);
    }

    @Test
    void createUser_whenInvoked_thenReturnSavedUserDto() {
        when(userService.save(any())).thenReturn(user);

        actual = userController.createUser(inUserDto);

        assertEquals(expected, actual);
    }

    @Test
    void updateUser_whenUpdateUser_thenReturnUpdatedUser() {
        User oldUser = new User();
        when(userService.findById(userId)).thenReturn(oldUser);
        when(userService.update(any(), any())).thenReturn(user);

        actual = userController.updateUser(userId, inUserDto);

        assertEquals(expected, actual);
    }

    @Test
    void deleteUser_whenInvoked_thenDeletingInServiceInvokedToo() {
        userController.deleteUser(userId);

        verify(userService, only()).deleteById(userId);
    }
}