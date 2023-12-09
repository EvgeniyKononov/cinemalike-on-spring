package ru.astondevs.cinemalike_on_spring.like.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.astondevs.cinemalike_on_spring.like.service.LikeService;

@RestController
@RequestMapping(path = "/like")
public class LikeController {
    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void setLike(@RequestParam long userId, @RequestParam long filmId) {
        likeService.setLike(userId, filmId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLike(@RequestParam long userId, @RequestParam long filmId) {
        likeService.deleteLike(userId, filmId);
    }
}
