package ru.astondevs.cinemalike_on_spring.user.service;

import ru.astondevs.cinemalike_on_spring.user.model.User;

public interface UserService {
    User findById(Long id);

    User save(User user);

    User update(User user, User updatedUser);

    void deleteById(Long id);
}
