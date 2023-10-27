package br.com.collec.payload.user;

public record OnlyUserResponseDTO (
        String id,
        String firstName,
        String lastName,
        String email
) {
}
