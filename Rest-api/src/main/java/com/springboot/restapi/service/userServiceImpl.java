package com.springboot.restapi.service;

import com.springboot.restapi.dto.UserDto;
import com.springboot.restapi.entity.User;
import com.springboot.restapi.exception.EmailAlreadyExistException;
import com.springboot.restapi.exception.ResourceNotFoundException;
import com.springboot.restapi.mapper.UserMapper;
import com.springboot.restapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class userServiceImpl implements UserService {

    private UserRepository userRepository;
    @Override
    public UserDto createUser(UserDto userDto) {
        Optional<User> user = userRepository.findByEmail(userDto.getEmail());
        if(user.isPresent()){
            throw new EmailAlreadyExistException("Email already exist with this user");
        }
        User saveduser = UserMapper.mapToUser(userDto);
        User savedUser1 =  userRepository.save(saveduser);
        UserDto userDto1= UserMapper.mapToUserDto(savedUser1);
        return userDto1;
    }

    @Override
    public Optional<User> getUserById(Long id)  {
      return userRepository.findById(id);
    }

    @Override
    public List<UserDto> getAllUser() {
       List<User> user = userRepository.findAll();
       return user.stream().map(UserMapper::mapToUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(User user) {
        User userData = userRepository.save(user);
        return UserMapper.mapToUserDto(userData);
    }

    @Override
    public void deleteUser(Long id) throws ResourceNotFoundException {
            userRepository.deleteById(id);
    }

}
