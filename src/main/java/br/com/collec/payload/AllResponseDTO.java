package br.com.collec.payload;

public record AllResponseDTO<T>(
        T content,
        int pageNo,
        int pageSize,
        int totalPages,
        long totalElements,
        boolean last
) {}


