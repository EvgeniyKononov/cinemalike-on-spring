package ru.astondevs.cinemalike_on_spring.user.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.astondevs.cinemalike_on_spring.exception.DuplicateException;
import ru.astondevs.cinemalike_on_spring.exception.NotFoundException;
import ru.astondevs.cinemalike_on_spring.user.model.User;
import ru.astondevs.cinemalike_on_spring.user.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.only;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository repository;
    @InjectMocks
    private UserServiceImpl service;
    private User expected;
    private User actual;
    private Long userId;
    private String userLogin;

    @BeforeEach
    void setUp() {
        userId = 1L;
        userLogin = "sherminator";
        expected = new User(userId, userLogin, "sherman");
    }


    @Test
    void findById_whenUserFound_thenReturnThisUser() {
        when(repository.findById(userId)).thenReturn(Optional.ofNullable(expected));

        actual = service.findById(userId);

        assertEquals(expected, actual);
    }

    @Test
    void findById_whenUserNotFound_thenThrowNotFoundException() {
        when(repository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> service.findById(userId));
    }

    @Test
    void save_whenUserSaved_thenReturnUser() {
        when(repository.findByLogin(userLogin)).thenReturn(Optional.empty());
        when(repository.save(expected)).thenReturn(expected);

        actual = service.save(expected);

        assertEquals(expected, actual);
    }

    @Test
    void save_whenUserAlreadyExist_thenThrowDuplicateException() {
        when(repository.findByLogin(userLogin)).thenReturn(Optional.ofNullable(expected));

        assertThrows(DuplicateException.class,
                () -> service.save(expected));

        verify(repository, never()).save(expected);
    }

    @Test
    void update_whenUpdateUser_thenReturnUpdatedUser() {
        when(repository.save(any())).thenReturn(expected);

        actual = service.update(expected, new User());

        assertEquals(expected, actual);
    }

    @Test
    void deleteById_whenDeleteUser_thenInvokeDeletingFromRepository() {
        service.deleteById(userId);

        verify(repository, only()).deleteById(userId);
    }
}