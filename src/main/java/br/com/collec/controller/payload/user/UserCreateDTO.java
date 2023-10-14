package br.com.collec.controller.payload.user;

import br.com.collec.entity.CollectionsMovies;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class UserCreateDTO {

    @NotBlank(message = "first name must not be empty")
    @Size(min = 3, message = "first name must be greater than 3 letters")
    private String firstName;

    @NotBlank(message = "first name must not be empty")
    @Size(min = 3, message = "first name must be greater than 3 letters")
    private String lastName;

    @NotBlank(message = "email name must not be empty")
    @Email(message = "this field must be an email pattern")
    private String email;

    @NotBlank(message = "first name must not be empty")
    @Size(min = 3, message = "first name must be greater than 3 letters")
    private String password;

    private List<CollectionsMovies> collectionsMovies;

    public UserCreateDTO(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
