package br.com.mercadoenvios.logitrack.domain.model;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record User(
        String id,
        String name,
        String email,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
