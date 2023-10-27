package br.com.collec.payload.user;

import br.com.collec.entity.CollectionsMovies;

import java.util.List;

public record UserResponseDTO(
        String id,
        String firstName,
        String lastName,
        String email,
        List<CollectionsMovies> collectionsMovies
) {}
