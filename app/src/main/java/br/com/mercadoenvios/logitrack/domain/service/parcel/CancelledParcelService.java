package br.com.mercadoenvios.logitrack.domain.service.parcel;

import br.com.mercadoenvios.logitrack.domain.exception.ParcelNotFoundException;
import br.com.mercadoenvios.logitrack.domain.mapper.ParcelMapper;
import br.com.mercadoenvios.logitrack.domain.model.Parcel;
import br.com.mercadoenvios.logitrack.domain.usecase.validations.parcel.CancelledParcelValidation;
import br.com.mercadoenvios.logitrack.infrastructure.database.entity.ParcelEntity;
import br.com.mercadoenvios.logitrack.infrastructure.database.mapper.ParcelEntityMapper;
import br.com.mercadoenvios.logitrack.infrastructure.database.repository.parcel.ParcelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class CancelledParcelService {
    private final ParcelRepository repository;
    private final ParcelMapper parcelMapper;
    private final ParcelEntityMapper parcelEntityMapper;
    private final CancelledParcelValidation validation;

    public Mono<Parcel> cancelledParcel(Parcel parcel) {
        return validation.validate(parcel)
                .then(Mono.defer(() -> parcelEntityMapper.map(parcel)))
                .flatMap(this::updateParcel)
                .then(Mono.defer(() -> findParcelById(parcel.id())))
                .flatMap(parcelMapper::map);
    }

    private Mono<ParcelEntity> findParcelById(String id) {
        return repository.findParcelById(id)
                .switchIfEmpty(Mono.error(ParcelNotFoundException::new));
    }

    private Mono<Void> updateParcel(ParcelEntity parcelEntity) {
        return repository.updateCancelledParcel(parcelEntity);
    }
}
