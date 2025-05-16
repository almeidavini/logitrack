package br.com.mercadoenvios.logitrack.infrastructure.database.entity;

import lombok.Builder;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Builder
@Table("users")
public record UserEntity (
        @Column("id")
        String id,

        @Column("name")
        String name,

        @Column("email")
        String email,

        @Column("status")
        String status,

        @Column("created_at")
        LocalDateTime createdAt,

        @Column("updated_at")
        LocalDateTime updatedAt
){
}
