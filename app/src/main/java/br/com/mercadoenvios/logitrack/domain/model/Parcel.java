package br.com.mercadoenvios.logitrack.domain.model;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record Parcel(
        String id,
        String description,
        String senderId,
        String recipientId,
        String funFact,
        Boolean isHoliday,
        LocalDate estimatedDeliveryDate,
        Status status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deliveredAt
) {
    public enum Status {
        CREATED,
        IN_TRANSIT,
        DELIVERED,
        CANCELLED
    }
}
