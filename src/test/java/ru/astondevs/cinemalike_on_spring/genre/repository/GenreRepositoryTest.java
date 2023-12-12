package ru.astondevs.cinemalike_on_spring.genre.repository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.astondevs.cinemalike_on_spring.configuration_for_repositories.PersistenceJPATestContainerConfig;
import ru.astondevs.cinemalike_on_spring.film.repository.FilmRepository;
import ru.astondevs.cinemalike_on_spring.genre.model.Genre;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static ru.astondevs.cinemalike_on_spring.configuration_for_repositories.PersistenceJPATestContainerConfig.postgres;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PersistenceJPATestContainerConfig.class, GenreRepository.class, FilmRepository.class})
class GenreRepositoryTest {
    @Autowired
    private GenreRepository repository;
    @Autowired
    FilmRepository filmRepository;
    private Genre expected;
    private Genre actual;

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
        expected = new Genre(1L, "action");
    }

    @AfterEach
    void afterEach() {
        repository.deleteAll();
    }

    @Test
    void findById_whenGenreNotFound_thenReturnGenreWithNullFields() {
        Genre genre = new Genre(expected.getName());

        repository.save(genre);
        Optional <Genre> actual = repository.findById(2L);

        assertFalse(actual.isPresent());
    }

    @Test
    void findByName_whenGenreFound_thenReturnGenre() {
        Genre genre = new Genre(expected.getName());

        repository.save(genre);
        Optional <Genre> actual = repository.findByName(genre.getName());

        assertEquals(expected.getName(), actual.get().getName());
    }

    @Test
    void findByName_whenGenreNotFound_thenReturnGenreWithNullFields() {
        Genre genre = new Genre(expected.getName());

        repository.save(genre);
        Optional <Genre> actual = repository.findByName("new genre");

        assertFalse(actual.isPresent());
    }

    @Test
    void save_whenGenreSaved_thenReturnSavedGenre() {
        Genre genre = new Genre(expected.getName());

        actual = repository.save(genre);

        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    void update_whenGenreUpdated_thenReturnGenre() {
        Genre genre = new Genre(expected.getName());
        String newGenre = "new genre";
        expected.setName(newGenre);

        repository.save(genre);
        genre = new Genre(expected.getId(), newGenre);
        actual = repository.save(genre);

        assertEquals(expected.getName(), actual.getName());
    }


}