package com.springboot.restapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private long userId;
    // firstName should not be empty and not null
    @NotEmpty(message = "firstName should not be empty and not null")
    private String firstName;
    // lastName should not be empty and not null
    @NotEmpty(message = "last name should not be empty and not null")
    private String lastName;
    // email should not be empty and not null
    // email should be valid
    @NotEmpty(message = "email should not be empty and not null")
    @Email(message = "email should be valid")
    private String email;
}
