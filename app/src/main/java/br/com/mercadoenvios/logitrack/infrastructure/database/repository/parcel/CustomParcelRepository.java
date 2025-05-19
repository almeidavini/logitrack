package br.com.mercadoenvios.logitrack.infrastructure.database.repository.parcel;

import br.com.mercadoenvios.logitrack.infrastructure.database.entity.ParcelEntity;
import reactor.core.publisher.Mono;

public interface CustomParcelRepository {
    Mono<ParcelEntity> findParcelById(String id);

    Mono<Void> updateStatusParcel(ParcelEntity parcelEntity);

    Mono<Void> updateCancelledParcel(ParcelEntity parcelEntity);

    Mono<Void> updateDeliveredParcel(ParcelEntity parcelEntity);
}
