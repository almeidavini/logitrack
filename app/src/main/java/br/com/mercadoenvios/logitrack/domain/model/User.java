package br.com.mercadoenvios.logitrack.domain.model;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record User(
        String id,
        String name,
        String email,
        Status status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public enum Status {
        ACTIVE,
        INACTIVE
    }
}
