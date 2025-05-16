package br.com.mercadoenvios.logitrack.infrastructure.database.mapper;

import br.com.mercadoenvios.logitrack.domain.model.User;
import br.com.mercadoenvios.logitrack.infrastructure.database.entity.UserEntity;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
public class UserEntityMapper {
    public Mono<UserEntity> map(User user) {
        return Mono.just(UserEntity.builder()
                .id(user.id())
                .name(user.name())
                .email(user.email())
                .build());
    }

    public UserEntity map(Row row, RowMetadata metadata) {
        return UserEntity.builder()
                .id(row.get("id", String.class))
                .name(row.get("name", String.class))
                .email(row.get("email", String.class))
                .status(row.get("status", String.class))
                .createdAt(row.get("created_at", LocalDateTime.class))
                .updatedAt(row.get("updated_at", LocalDateTime.class))
                .build();
    }
}
