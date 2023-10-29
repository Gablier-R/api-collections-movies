package br.com.collec.api.payload.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record UserDataDTO(
        @NotBlank(message = "first name must not be empty")
        @Size(min = 3, message = "first name must be greater than 3 letters")
        String firstName,

        @NotBlank(message = "first name must not be empty")
        @Size(min = 3, message = "first name must be greater than 3 letters")
        String lastName,

        @NotBlank(message = "email name must not be empty")
        @Email(message = "this field must be an email pattern")
        String email,

        @NotBlank(message = "first name must not be empty")
        @Size(min = 3, message = "first name must be greater than 3 letters")
        String password
) {}
