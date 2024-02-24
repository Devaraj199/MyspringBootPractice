package com.springboot.restapi.controller;

import com.springboot.restapi.dto.UserDto;
import com.springboot.restapi.entity.User;
import com.springboot.restapi.exception.ResourceNotFoundException;
import com.springboot.restapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "CRUD operation user API",
    description = "Create User,Update user,delete user,get user")
@RestController
@AllArgsConstructor
@RequestMapping("api/user")

public class UserController {
    UserService userService;

    // Build create user REST API
    @Operation(summary = "Create user REST API",
    description = "Create user Rest api to create new user")
    @ApiResponse(responseCode = "201",description = "Http status 201 created")
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto user){
            UserDto saveUser =  userService.createUser(user);
            return new ResponseEntity<UserDto>(saveUser, HttpStatus.CREATED);
    }

    // Build get by id  REST API
    // http://localhost:8080/api/user/5
    @Operation(summary = "Get user By Id",
            description = "get  single user information from db ")
    @ApiResponse(responseCode = "200",description = "Got id")
    @GetMapping("{id}")
    public ResponseEntity <User> getUserById(@PathVariable  Long id) throws ResourceNotFoundException {
      User user =  userService.getUserById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));
      return new ResponseEntity<>(user,HttpStatus.OK);
    }

    // Build get all user REST API
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUser(){
        List<UserDto> userList = userService.getAllUser();
        return new ResponseEntity<>(userList,HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user) throws Exception {
        User existingUser =  userService.getUserById(user.getUserId()).orElseThrow(()->new ResourceNotFoundException("user","id",user.getUserId()));
        if(existingUser!=null){
            existingUser.setUserId(user.getUserId());
            existingUser.setEmail(user.getEmail());
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            UserDto updatedUser = userService.updateUser(existingUser);
            return ResponseEntity.ok(updatedUser);
        }
        return new ResponseEntity<>(user,HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) throws ResourceNotFoundException {
      User existingUser =  userService.getUserById(id).orElseThrow(()->new ResourceNotFoundException("user","id",id));
       if(existingUser!=null){
            userService.deleteUser(id);
            return new ResponseEntity<>("User successfully deleted",HttpStatus.OK);
        }
        return new ResponseEntity<>("user not found",HttpStatus.NOT_FOUND);
    }

    // Exception handling respective to this controller
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException rnfe, WebRequest wr){
//        ErrorDetails errorDetails = new ErrorDetails(
//                LocalDateTime.now(),
//                rnfe.getMessage(),
//                wr.getDescription(false),
//                "User_Not_Found"
//        );
//       return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
//    }
}
