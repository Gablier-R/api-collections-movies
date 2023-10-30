package br.com.collec.api.payload.user;

import java.time.LocalDateTime;

public record OnlyUserResponseDTO (
        String id,
        String firstName,
        String lastName,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updateAt

) {}
