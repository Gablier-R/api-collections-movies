package br.com.collec.payload.user;

import br.com.collec.entity.CollectionsMovies;
import br.com.collec.payload.collectionsMovies.CollectionsResponseDTO;

import java.util.List;

public record UserResponseDTO(
        String id,
        String firstName,
        String lastName,
        String email,
        List<CollectionsMovies> collectionsMovies
) { }
