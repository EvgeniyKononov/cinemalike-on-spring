package ru.astondevs.cinemalike_on_spring.user.model;

import jakarta.persistence.*;
import ru.astondevs.cinemalike_on_spring.film.model.Film;

import java.util.*;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "film_likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "film_id")
    )
    private List<Film> filmLikes;

    public User() {
        this.filmLikes = new ArrayList<>();
    }

    public User(Long id, String login, String name) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.filmLikes = new ArrayList<>();
    }

    public User(String login, String name) {
        this.login = login;
        this.name = name;
        this.filmLikes = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Film> getFilmLikes() {
        return filmLikes;
    }

    public void setFilmLikes(List<Film> filmLikes) {
        this.filmLikes = filmLikes;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(login, user.login)
                && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, name, filmLikes);
    }
}
