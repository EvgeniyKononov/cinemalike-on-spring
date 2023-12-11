package ru.astondevs.cinemalike_on_spring.user.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ru.astondevs.cinemalike_on_spring.user.controller.dto.InUserDto;
import ru.astondevs.cinemalike_on_spring.user.controller.dto.OutUserDto;
import ru.astondevs.cinemalike_on_spring.user.controller.mapper.UserDtoMapper;
import ru.astondevs.cinemalike_on_spring.user.model.User;
import ru.astondevs.cinemalike_on_spring.user.service.UserService;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerMockMvcTest {
    @Mock
    private UserService userService;
    @Mock
    private UserDtoMapper dtoMapper;
    @InjectMocks
    private UserController userController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Long userId;
    private OutUserDto expected;
    private User user;
    private InUserDto inUserDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
        userId = 1L;
        expected = new OutUserDto(1L, "name", new ArrayList<>());
        user = new User(1L, "login", "name");
        inUserDto = new InUserDto();
    }

    @Test
    void getUserById_whenInvokedWithCorrectId_thenReturnDtoAndStatusOk() throws Exception {
        when(userService.findById(userId)).thenReturn(user);
        when(dtoMapper.map(user)).thenReturn(expected);

        String actual = mockMvc.perform(
                        get("/user")
                                .param("id", String.valueOf(userId)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expected), actual);
    }

    @Test
    void getUserById_whenInvokedWithIncorrectId_thenStatusBadRequest() throws Exception {
        mockMvc.perform(
                        get("/user")
                                .param("id", "Not Number"))
                .andExpect(status().isBadRequest());

        verify(userService, never()).save(user);
    }

    @Test
    void createUser_whenInvoked_thenReturnSavedUserDtoAndStatusCreated() throws Exception {
        when(dtoMapper.map(inUserDto)).thenReturn(user);
        when(userService.save(user)).thenReturn(user);
        when(dtoMapper.map(user)).thenReturn(expected);

        String actual = mockMvc.perform(
                        post("/user")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(inUserDto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expected), actual);
    }

    @Test
    void updateUser_whenUpdateUser_thenReturnUpdatedUserStatusOk() throws Exception {
        when(userService.findById(userId)).thenReturn(user);
        when(dtoMapper.map(inUserDto)).thenReturn(user);
        when(userService.update(user, user)).thenReturn(user);
        when(dtoMapper.map(user)).thenReturn(expected);

        String actual = mockMvc.perform(
                        patch("/user")
                                .contentType("application/json")
                                .param("id", String.valueOf(userId))
                                .content(objectMapper.writeValueAsString(inUserDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expected), actual);
    }

    @Test
    void deleteUser_whenInvokedWithCorrectId_thenStatusNoContent() throws Exception {
        mockMvc.perform(
                        delete("/user")
                                .param("id", String.valueOf(userId)))
                .andExpect(status().isNoContent());

        verify(userService, only()).deleteById(userId);
    }

    @Test
    void deleteUser_whenInvokedWithIncorrectId_thenStatusBadRequest() throws Exception {
        mockMvc.perform(
                        delete("/user")
                                .param("id", "Not Number"))
                .andExpect(status().isBadRequest());

        verify(userService, never()).deleteById(userId);
    }
}

