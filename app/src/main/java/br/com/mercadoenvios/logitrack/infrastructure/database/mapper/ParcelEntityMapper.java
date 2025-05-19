package br.com.mercadoenvios.logitrack.infrastructure.database.mapper;

import br.com.mercadoenvios.logitrack.domain.model.Parcel;
import br.com.mercadoenvios.logitrack.infrastructure.database.entity.ParcelEntity;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class ParcelEntityMapper {
    public Mono<ParcelEntity> map(Parcel parcel) {
        return Mono.just(ParcelEntity.builder()
                .id(parcel.id())
                .description(parcel.description())
                .senderId(parcel.senderId())
                .recipientId(parcel.recipientId())
                .status(Optional.ofNullable(parcel.status())
                        .map(Parcel.Status::name).orElse(null))
                .funFact(parcel.funFact())
                .isHoliday(parcel.isHoliday())
                .estimatedDeliveryDate(parcel.estimatedDeliveryDate())
                .createdAt(parcel.createdAt())
                .updatedAt(parcel.updatedAt())
                .deliveredAt(
                        Parcel.Status.DELIVERED.equals(parcel.status())
                                ? LocalDateTime.now()
                                : parcel.deliveredAt()
                )
                .build());
    }

    public ParcelEntity map(Row row, RowMetadata metadata) {
        return ParcelEntity.builder()
                .id(row.get("id", String.class))
                .description(row.get("description", String.class))
                .senderId(row.get("sender_id", String.class))
                .recipientId(row.get("recipient_id", String.class))
                .funFact(row.get("fun_fact", String.class))
                .isHoliday(row.get("is_holiday", Boolean.class))
                .estimatedDeliveryDate(row.get("estimated_delivery_date", LocalDate.class))
                .status(row.get("status", String.class))
                .createdAt(row.get("created_at", LocalDateTime.class))
                .updatedAt(row.get("created_at", LocalDateTime.class))
                .deliveredAt(row.get("delivered_at", LocalDateTime.class))
                .build();
    }

}
