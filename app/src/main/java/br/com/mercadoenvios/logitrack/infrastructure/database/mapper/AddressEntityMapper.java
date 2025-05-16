package br.com.mercadoenvios.logitrack.infrastructure.database.mapper;

import br.com.mercadoenvios.logitrack.domain.model.Address;
import br.com.mercadoenvios.logitrack.domain.model.User;
import br.com.mercadoenvios.logitrack.infrastructure.database.entity.AddressEntity;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class AddressEntityMapper {
    public Mono<AddressEntity> map(Address address, User user) {
        return Mono.just(AddressEntity.builder()
                .id(UUID.randomUUID().toString())
                .userId(user.id())
                .street(address.street())
                .number(address.number())
                .city(address.city())
                .state(address.state())
                .zipCode(address.zipCode())
                .country(address.country())
                .build());
    }

    public AddressEntity map(Row row, RowMetadata metadata) {
        return AddressEntity.builder()
                .id(row.get("id", String.class))
                .userId(row.get("user_id", String.class))
                .street(row.get("street", String.class))
                .number(row.get("number", String.class))
                .city(row.get("city", String.class))
                .state(row.get("state", String.class))
                .zipCode(row.get("zip_code", String.class))
                .country(row.get("country", String.class))
                .createdAt(row.get("created_at", LocalDateTime.class))
                .updatedAt(row.get("updated_at", LocalDateTime.class))
                .build();
    }
}
