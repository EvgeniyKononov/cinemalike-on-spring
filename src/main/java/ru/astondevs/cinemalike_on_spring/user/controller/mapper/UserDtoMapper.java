package ru.astondevs.cinemalike_on_spring.user.controller.mapper;

import org.mapstruct.Mapper;
import ru.astondevs.cinemalike_on_spring.film.model.Film;
import ru.astondevs.cinemalike_on_spring.user.controller.dto.InUserDto;
import ru.astondevs.cinemalike_on_spring.user.model.User;
import ru.astondevs.cinemalike_on_spring.user.controller.dto.OutUserDto;

import java.util.*;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    default OutUserDto map(User user) {
        List<String> filmNames = new ArrayList<>();
        if (Objects.nonNull(user.getFilmLikes())) {
            for (Film film : user.getFilmLikes()) {
                filmNames.add(film.getName());
            }
        }
        return new OutUserDto(user.getId(), user.getName(), filmNames);
    }

    User map(InUserDto inUserDto);

    Set<OutUserDto> map(Set<User> users);
}
