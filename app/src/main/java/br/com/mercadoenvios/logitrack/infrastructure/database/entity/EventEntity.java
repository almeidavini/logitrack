package br.com.mercadoenvios.logitrack.infrastructure.database.entity;

import lombok.Builder;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Builder
@Table("events")
public record EventEntity(
        @Column("id")
        String id,

        @Column("parcel_id")
        String parcelId,

        @Column("location")
        String location,

        @Column("description")
        String description,

        @Column("created_at")
        LocalDateTime createdAt
) {
}