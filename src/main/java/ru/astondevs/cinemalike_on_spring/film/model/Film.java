package ru.astondevs.cinemalike_on_spring.film.model;

import jakarta.persistence.*;
import ru.astondevs.cinemalike_on_spring.genre.model.Genre;
import ru.astondevs.cinemalike_on_spring.user.model.User;

import java.util.*;

@Entity
@Table(name = "films")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "genre", referencedColumnName = "id")
    private Genre genre;
    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(name = "film_likes",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> userLikes;

    public Film() {
        userLikes = new ArrayList<>();
    }

    public Film(Long id, String name, List<User> userLikes, Genre genre) {
        this.id = id;
        this.name = name;
        this.userLikes = userLikes;
        this.genre = genre;
    }

    public Film(String name, Genre genre) {
        this.name = name;
        this.genre = genre;
        userLikes = new ArrayList<>();
    }

    public Film(Long id, String name, Genre genre) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        userLikes = new ArrayList<>();
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

    public List<User> getUserLikes() {
        return userLikes;
    }

    public void setUserLikes(List<User> userLikes) {
        this.userLikes = userLikes;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return Objects.equals(id, film.id) && Objects.equals(name, film.name)
                && Objects.equals(genre, film.genre) && Objects.equals(userLikes, film.userLikes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, genre, userLikes);
    }
}

