package br.com.collec.payload.collectionsMovies;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CollectionsDataDTO(
        @NotBlank(message = "name must not be empty")
        @Size(min = 3, message = "name must be greater than 3 letters")
        String name,

        @NotBlank(message = "name must not be empty")
        @Size(min = 3, message = "name must be greater than 3 letters")
        String resume
) {}
