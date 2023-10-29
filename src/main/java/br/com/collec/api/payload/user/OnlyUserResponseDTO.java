package br.com.collec.api.payload.user;

public record OnlyUserResponseDTO (
        String id,
        String firstName,
        String lastName,
        String email
) {}
