package br.com.mercadoenvios.logitrack.infrastructure.database.mapper;

import br.com.mercadoenvios.logitrack.domain.model.Event;
import br.com.mercadoenvios.logitrack.infrastructure.database.entity.EventEntity;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class EventEntityMapper {
    public EventEntity map(Row row, RowMetadata metadata) {
        return EventEntity.builder()
                .id(row.get("id", String.class))
                .parcelId(row.get("parcel_id", String.class))
                .location(row.get("location", String.class))
                .description(row.get("description", String.class))
                .createdAt(row.get("created_at", LocalDateTime.class))
                .build();
    }

    public EventEntity map(Event event) {
        return EventEntity.builder()
                .id(UUID.randomUUID().toString())
                .parcelId(event.parcelId())
                .location(event.location())
                .description(event.description())
                .build();
    }
}
