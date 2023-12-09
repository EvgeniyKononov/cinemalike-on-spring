package ru.astondevs.cinemalike_on_spring.user.controller.dto;

import java.util.List;
import java.util.Objects;

public class OutUserDto {
    private Long id;
    private String name;
    private List<String> filmLikes;

    public OutUserDto() {
    }

    public OutUserDto(Long id, String name, List<String> filmLikes) {
        this.id = id;
        this.name = name;
        this.filmLikes = filmLikes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getFilmLikes() {
        return filmLikes;
    }

    public void setFilmLikes(List<String> filmLikes) {
        this.filmLikes = filmLikes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OutUserDto that = (OutUserDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name)
                && Objects.equals(filmLikes, that.filmLikes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, filmLikes);
    }
}
