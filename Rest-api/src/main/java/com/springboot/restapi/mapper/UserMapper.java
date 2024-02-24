package com.springboot.restapi.mapper;

import com.springboot.restapi.dto.UserDto;
import com.springboot.restapi.entity.User;

public class UserMapper {
    public static UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto(user.getUserId(),
                user.getFirstName(),
                user.getLastName(), user.getEmail());
        return userDto;
    }

    public static User mapToUser(UserDto userDto) {
        User user = new User(userDto.getUserId(),
                userDto.getFirstName(),
                userDto.getLastName(), userDto.getEmail());
        return user;
    }
}
