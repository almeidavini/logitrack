package br.com.mercadoenvios.logitrack.domain.model;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Address(
        String id,
        String userId,
        String street,
        String number,
        String city,
        String state,
        String zipCode,
        String country,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
