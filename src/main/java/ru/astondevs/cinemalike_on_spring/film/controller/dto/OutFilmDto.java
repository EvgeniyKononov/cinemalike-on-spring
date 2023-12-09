package ru.astondevs.cinemalike_on_spring.film.controller.dto;

import ru.astondevs.cinemalike_on_spring.user.controller.dto.OutUserDto;

import java.util.List;
import java.util.Objects;

public class OutFilmDto {
    private String name;
    private String genre;
    private List<OutUserDto> userLikes;

    public OutFilmDto() {
    }

    public OutFilmDto(String name, List<OutUserDto> userLikes, String genre) {
        this.name = name;
        this.userLikes = userLikes;
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OutUserDto> getUserLikes() {
        return userLikes;
    }

    public void setUserLikes(List<OutUserDto> userLikes) {
        this.userLikes = userLikes;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OutFilmDto that = (OutFilmDto) o;
        return Objects.equals(name, that.name) && Objects.equals(userLikes, that.userLikes)
                && Objects.equals(genre, that.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, userLikes, genre);
    }

    @Override
    public String toString() {
        return "OutFilmDto{" +
                "name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", userLikes=" + userLikes +
                '}';
    }
}
