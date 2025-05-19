package br.com.mercadoenvios.logitrack.infrastructure.database.entity;

import lombok.Builder;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Table("parcels")
public record ParcelEntity(
        @Column("id")
        String id,

        @Column("description")
        String description,

        @Column("sender_id")
        String senderId,

        @Column("recipient_id")
        String recipientId,

        @Column("fun_fact")
        String funFact,

        @Column("is_holiday")
        Boolean isHoliday,

        @Column("estimated_delivery_date")
        LocalDate estimatedDeliveryDate,

        @Column("status")
        String status,

        @Column("created_at")
        LocalDateTime createdAt,

        @Column("updated_at")
        LocalDateTime updatedAt,

        @Column("delivered_at")
        LocalDateTime deliveredAt
) {
}
