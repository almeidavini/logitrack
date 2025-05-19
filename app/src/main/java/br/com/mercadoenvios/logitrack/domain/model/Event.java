package br.com.mercadoenvios.logitrack.domain.model;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Event(
        String id,
        String parcelId,
        String location,
        String description,
        LocalDateTime createdAt
) {
}
