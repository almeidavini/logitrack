package br.com.mercadoenvios.logitrack.domain.model;

import br.com.mercadoenvios.logitrack.infrastructure.config.CopyableRecord;
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
) implements CopyableRecord<Parcel> {
    public enum Status {
        CREATED,
        IN_TRANSIT,
        DELIVERED,
        CANCELLED;

        public boolean canTransitionTo(Status nextStatus) {
            return switch (this) {
                case CREATED -> nextStatus == IN_TRANSIT;
                case IN_TRANSIT -> nextStatus == DELIVERED;
                case DELIVERED, CANCELLED -> false;
            };
        }
    }
}
