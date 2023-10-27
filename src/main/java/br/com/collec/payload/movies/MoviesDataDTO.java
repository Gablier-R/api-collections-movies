package br.com.collec.payload.movies;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record MoviesDataDTO(
        String id,
        String url
) {}
