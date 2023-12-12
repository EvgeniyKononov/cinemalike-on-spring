package ru.astondevs.cinemalike_on_spring.film.repository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.astondevs.cinemalike_on_spring.configuration_for_repositories.PersistenceJPATestContainerConfig;
import ru.astondevs.cinemalike_on_spring.film.model.Film;
import ru.astondevs.cinemalike_on_spring.genre.model.Genre;
import ru.astondevs.cinemalike_on_spring.genre.repository.GenreRepository;
import ru.astondevs.cinemalike_on_spring.user.model.User;
import ru.astondevs.cinemalike_on_spring.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static ru.astondevs.cinemalike_on_spring.configuration_for_repositories.PersistenceJPATestContainerConfig.postgres;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PersistenceJPATestContainerConfig.class, GenreRepository.class, FilmRepository.class, UserRepository.class})
class FilmRepositoryTest {
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private UserRepository userRepository;
    private Film filmInDb;
    private Film actual;
    private Genre genreInDb;
    private User user1InDb;
    private User user2InDb;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void setUp() {
        genreInDb = genreRepository.save(new Genre("action"));
        user1InDb = userRepository.save(new User("sherminator", "sherman"));
        user2InDb = userRepository.save(new User("stifmaster", "stifler"));
        filmInDb = filmRepository.save(new Film("Terminator", genreInDb));
        filmInDb.setUserLikes(List.of(user1InDb, user2InDb));
        filmInDb = filmRepository.save(filmInDb);
    }

    @AfterEach
    void afterEach() {
        userRepository.deleteAll();
        filmRepository.deleteAll();
        genreRepository.deleteAll();
    }

    @Test
    void findById_whenFilmFound_thenReturnFilmWithGenreAndUserLikes() {
        Optional<Film> actual = filmRepository.findById(filmInDb.getId());

        assertEquals(filmInDb.getId(), actual.get().getId());
        assertEquals(filmInDb.getName(), actual.get().getName());
        assertEquals(genreInDb.getName(), actual.get().getGenre().getName());
        assertTrue(actual.get().getUserLikes().contains(user1InDb));
        assertTrue(actual.get().getUserLikes().contains(user2InDb));
    }

    @Test
    void update_whenFilmUpdated_thenReturnFilmWithNewNameAndGenre() {
        Genre newGenreInDb = genreRepository.save(new Genre("Drama"));
        String newName = "Titanic";

        actual = filmRepository.save(new Film(filmInDb.getId(), newName, List.of(user1InDb), newGenreInDb));

        assertEquals(filmInDb.getId(), actual.getId());
        assertEquals(newName, actual.getName());
        assertEquals(newGenreInDb.getName(), actual.getGenre().getName());
        assertTrue(actual.getUserLikes().contains(user1InDb));
    }

    @Test
    void delete_whenDeleteFilm_thenItNotInDbAndReturn() {
        filmRepository.delete(filmInDb);

        Optional<Film> actual = filmRepository.findById(filmInDb.getId());

        assertFalse(actual.isPresent());
    }

}