package com.example.travelwise.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO {
    private Long id;
    @NotBlank(message = "User email cannot be blank")
    private String email;
    @NotBlank(message = "User password cannot be blank")
    private String password;
    @NotBlank(message = "User first name cannot be blank")
    private String firstName;
    @NotBlank(message = "User surname cannot be blank")
    private String surname;
    private LocalDate birthDate;
    private String phoneNumber;
    private String profilePictureUrl;
    private Long cartId;
}
