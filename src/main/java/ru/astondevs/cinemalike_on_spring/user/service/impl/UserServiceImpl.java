package ru.astondevs.cinemalike_on_spring.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.astondevs.cinemalike_on_spring.exception.DuplicateException;
import ru.astondevs.cinemalike_on_spring.exception.NotFoundException;
import ru.astondevs.cinemalike_on_spring.user.model.User;
import ru.astondevs.cinemalike_on_spring.user.repository.UserRepository;
import ru.astondevs.cinemalike_on_spring.user.service.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with such id not found"));
    }

    @Override
    public User save(User user) {
        Optional<User> userInDb = userRepository.findByLogin(user.getLogin());
        if (userInDb.isPresent()) {
            throw new DuplicateException("User with such login already exist");
        }
        return userRepository.save(user);
    }

    @Override
    public User update(User user, User updatedUser) {
        return userRepository.save(new User(user.getId(), updatedUser.getLogin(), updatedUser.getName()));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}