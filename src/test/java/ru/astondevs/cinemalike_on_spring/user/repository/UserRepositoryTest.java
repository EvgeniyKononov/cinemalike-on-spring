package ru.astondevs.cinemalike_on_spring.user.repository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.astondevs.cinemalike_on_spring.configuration_for_repositories.PersistenceJPATestContainerConfig;
import ru.astondevs.cinemalike_on_spring.user.model.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static ru.astondevs.cinemalike_on_spring.configuration_for_repositories.PersistenceJPATestContainerConfig.postgres;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PersistenceJPATestContainerConfig.class, UserRepository.class})
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;
    private User expected;
    private User actual;

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
        expected = new User(1L, "sherminator", "sherman");
    }

    @AfterEach
    void afterEach() {
        repository.deleteAll();
    }

    @Test
    void findById_whenUserNotFound_thenReturnUserWithNullFields() {
        User user = new User(expected.getLogin(), expected.getName());

        Optional<User> actual = repository.findById(100L);

        assertEquals(Optional.empty(), actual);
    }

    @Test
    void findByLogin_whenUserFound_thenReturnUser() {
        User user = new User(expected.getLogin(), expected.getName());

        repository.save(user);
        Optional<User> actual = repository.findByLogin(expected.getLogin());

        assertEquals(expected, actual.get());
    }

    @Test
    void findByLogin_whenUserNotFound_thenReturnUserWithNullFields() {
        User user = new User(expected.getLogin(), expected.getName());

        repository.save(user);
        Optional<User> actual = repository.findByLogin("new user");

        assertEquals(Optional.empty(), actual);
    }

    @Test
    void save_whenSaveUser_thenReturnSavedUser() {
        User user = new User(expected.getLogin(), expected.getName());

        actual = repository.save(user);

        assertEquals(expected.getLogin(), actual.getLogin());
    }


    @Test
    void delete_whenUserDeleted_thenItNotInDb() {
        User user = new User(expected.getLogin(), expected.getName());

        actual = repository.save(user);
        repository.delete(actual);
        Optional<User> newActual = repository.findById(actual.getId());
        Iterable<User> users = repository.findAll();

        assertEquals(Optional.empty(), newActual);
    }


}