package ru.astondevs.cinemalike_on_spring.like.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.cinemalike_on_spring.like.service.LikeService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LikeControllerTest {
    @Mock
    private LikeService likeService;
    @InjectMocks
    private LikeController likeController;
    private Long userId;
    private Long filmId;

    @BeforeEach
    void setUp() {
        userId = 1L;
        filmId = 1L;
    }

    @Test
    void setLike_whenInvoked_thenSettingInServiceInvokedToo() {
        likeController.setLike(userId, filmId);

        verify(likeService, only()).setLike(userId, filmId);
    }

    @Test
    void deleteLike_whenInvoked_thenDeletingInServiceInvokedToo() {
        likeController.deleteLike(userId, filmId);

        verify(likeService, only()).deleteLike(userId, filmId);
    }
}