package br.com.collec.api.payload.user;

import br.com.collec.domain.entity.CollectionsMovies;

import java.time.LocalDateTime;
import java.util.List;

public record UserResponseDTO(
        String id,
        String firstName,
        String lastName,
        String email,
        List<CollectionsMovies> collectionsMovies,
        LocalDateTime createdAt,
        LocalDateTime updateAt
) {}
