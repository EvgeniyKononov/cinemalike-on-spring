package ru.astondevs.cinemalike_on_spring.like.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.astondevs.cinemalike_on_spring.like.service.LikeService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LikeControllerMockMvcTest {
    @Mock
    private LikeService likeService;
    @InjectMocks
    private LikeController likeController;
    private MockMvc mockMvc;
    private Long userId;
    private Long filmId;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(likeController).build();
        userId = 1L;
        filmId = 1L;
    }

    @Test
    void setLike_whenInvoked_thenSettingInServiceInvokedTooAndStatusCreated() throws Exception {
        mockMvc.perform(
                        post("/like")
                                .contentType("application/json")
                                .param("userId", String.valueOf(userId))
                                .param("filmId", String.valueOf(filmId)))
                .andExpect(status().isCreated());

        verify(likeService, only()).setLike(userId, filmId);
    }

    @Test
    void setLike_whenInvokedWithIncorrectId_thenSettingInServiceNotInvokedAndStatusBadRequest() throws Exception {
        mockMvc.perform(
                        post("/like")
                                .contentType("application/json")
                                .param("userId", "try to convert me to long")
                                .param("filmId", "try to convert me to long"))
                .andExpect(status().isBadRequest());

        verify(likeService, never()).setLike(userId, filmId);
    }

    @Test
    void deleteLike_whenInvoked_thenDeletingInServiceInvokedTooAndStatusNoContent() throws Exception {
        mockMvc.perform(
                        delete("/like")
                                .contentType("application/json")
                                .param("userId", String.valueOf(userId))
                                .param("filmId", String.valueOf(filmId)))
                .andExpect(status().isNoContent());

        verify(likeService, only()).deleteLike(userId, filmId);
    }

    @Test
    void deleteLike_whenInvokedWithIncorrectId_thenDeletingInServiceNotInvokedAndStatusBadRequest() throws Exception {
        mockMvc.perform(
                        delete("/like")
                                .contentType("application/json")
                                .param("userId", "try to convert me to long")
                                .param("filmId", "try to convert me to long"))
                .andExpect(status().isBadRequest());

        verify(likeService, never()).setLike(userId, filmId);
    }
}