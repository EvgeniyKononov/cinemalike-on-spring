package ru.astondevs.cinemalike_on_spring.like.service;

public interface LikeService {
    void setLike(Long userId, Long filmId);

    void deleteLike(Long userId, Long filmId);
}
