package ru.astondevs.cinemalike_on_spring.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.astondevs.cinemalike_on_spring.user.controller.dto.InUserDto;
import ru.astondevs.cinemalike_on_spring.user.model.User;
import ru.astondevs.cinemalike_on_spring.user.service.UserService;
import ru.astondevs.cinemalike_on_spring.user.controller.dto.OutUserDto;
import ru.astondevs.cinemalike_on_spring.user.controller.mapper.UserDtoMapper;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    private final UserService userService;
    private final UserDtoMapper dtoMapper;

    @Autowired
    public UserController(UserService userService, UserDtoMapper dtoMapper) {
        this.userService = userService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping
    public OutUserDto getUserById(@RequestParam long id) {
        User user = userService.findById(id);
        return dtoMapper.map(user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OutUserDto createUser(@RequestBody InUserDto inUserDto) {
        User user = dtoMapper.map(inUserDto);
        user = userService.save(user);
        return dtoMapper.map(user);
    }

    @PatchMapping
    public OutUserDto updateUser(@RequestParam long id, @RequestBody InUserDto inUserDto) {
        User oldUser = userService.findById(id);
        User newUser = dtoMapper.map(inUserDto);
        newUser = userService.update(oldUser, newUser);
        return dtoMapper.map(newUser);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@RequestParam long id) {
        userService.deleteById(id);
    }
}
