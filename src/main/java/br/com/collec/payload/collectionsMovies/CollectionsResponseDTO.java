package br.com.collec.payload.collectionsMovies;

import br.com.collec.entity.Movies;

import java.util.List;

public record CollectionsResponseDTO(
        String id,
        String name,
        String resume,
        List<Movies> movies,
        boolean isPublished
) {}

