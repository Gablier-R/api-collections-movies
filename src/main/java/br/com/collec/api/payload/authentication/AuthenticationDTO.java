package br.com.collec.api.payload.authentication;

public record AuthenticationDTO(
        String email,
        String password
) {
}
