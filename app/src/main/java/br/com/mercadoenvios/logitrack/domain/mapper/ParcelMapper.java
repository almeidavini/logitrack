package br.com.mercadoenvios.logitrack.domain.mapper;

import br.com.mercadoenvios.logitrack.application.dto.parcel.CreateParcelRequest;
import br.com.mercadoenvios.logitrack.application.dto.parcel.CreateParcelResponse;
import br.com.mercadoenvios.logitrack.application.dto.parcel.UpdateStatusParcelRequest;
import br.com.mercadoenvios.logitrack.domain.model.Parcel;
import br.com.mercadoenvios.logitrack.infrastructure.database.entity.ParcelEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class ParcelMapper {
    public Mono<Parcel> map(CreateParcelRequest request) {
        return Mono.just(Parcel.builder()
                .id(UUID.randomUUID().toString())
                .description(request.description())
                .senderId(request.senderId())
                .recipientId(request.recipientId())
                .estimatedDeliveryDate(request.estimatedDeliveryDate())
                .build());
    }

    public Mono<Parcel> map(ParcelEntity parcel) {
        return Mono.just(Parcel.builder()
                .id(parcel.id())
                .description(parcel.description())
                .senderId(parcel.senderId())
                .recipientId(parcel.recipientId())
                .funFact(parcel.funFact())
                .isHoliday(parcel.isHoliday())
                .estimatedDeliveryDate(parcel.estimatedDeliveryDate())
                .status(Parcel.Status.valueOf(parcel.status()))
                .createdAt(parcel.createdAt())
                .updatedAt(parcel.updatedAt())
                .deliveredAt(parcel.deliveredAt())
                .build());
    }

    public Mono<Parcel> map(String idParcel, UpdateStatusParcelRequest request) {
        return Mono.just(Parcel.builder()
                .id(idParcel)
                .status(Parcel.Status.valueOf(request.status()))
                .build());
    }

    public Mono<Parcel> map(String idParcel) {
        return Mono.just(Parcel.builder()
                .id(idParcel)
                .build());
    }
}
