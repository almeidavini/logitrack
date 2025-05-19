package br.com.mercadoenvios.logitrack.infrastructure.database.entity;

import lombok.Builder;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Builder
@Table("addresses")
public record AddressEntity(
        @Column("user_id")
        String userId,

        @Column("street")
        String street,

        @Column("number")
        String number,

        @Column("city")
        String city,

        @Column("state")
        String state,

        @Column("zip_code")
        String zipCode,

        @Column("country")
        String country,

        @Column("created_at")
        LocalDateTime createdAt,

        @Column("updated_at")
        LocalDateTime updatedAt
) {
}
