package br.com.collec.api.payload.collectionsMovies;

import br.com.collec.domain.entity.Movies;

import java.time.LocalDateTime;
import java.util.List;

public record CollectionsResponseDTO(
        String id,
        String name,
        String resume,
        List<Movies> movies,
        boolean isPublished,
        LocalDateTime createdAt,
        LocalDateTime updateAt
) {}

