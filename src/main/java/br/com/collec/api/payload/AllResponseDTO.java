package br.com.collec.api.payload;

public record AllResponseDTO<T>(
        T content,
        int pageNo,
        int pageSize,
        int totalPages,
        long totalElements,
        boolean last
) {}


