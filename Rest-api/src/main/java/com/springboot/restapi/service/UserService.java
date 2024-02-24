package com.springboot.restapi.service;

import com.springboot.restapi.dto.UserDto;
import com.springboot.restapi.entity.User;
import com.springboot.restapi.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDto createUser(UserDto user);
    Optional<User> getUserById(Long id);
    List<UserDto> getAllUser();
    UserDto updateUser(User user);
    void deleteUser(Long id) throws ResourceNotFoundException;
}
