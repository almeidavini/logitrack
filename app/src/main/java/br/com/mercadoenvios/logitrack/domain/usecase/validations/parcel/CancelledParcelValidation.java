package br.com.mercadoenvios.logitrack.domain.usecase.validations.parcel;

import br.com.mercadoenvios.logitrack.domain.exception.ParcelNotFoundException;
import br.com.mercadoenvios.logitrack.domain.exception.validation.CannotCancelParcelException;
import br.com.mercadoenvios.logitrack.domain.model.Parcel;
import br.com.mercadoenvios.logitrack.infrastructure.database.entity.ParcelEntity;
import br.com.mercadoenvios.logitrack.infrastructure.database.repository.parcel.ParcelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CancelledParcelValidation {
    private final ParcelRepository repository;

    public Mono<Parcel> validate(Parcel parcel) {
        return findParcelById(parcel.id())
                .flatMap(parcelEntity -> Parcel.Status.valueOf(parcelEntity.status()).equals(Parcel.Status.CREATED)
                        ? Mono.just(parcel)
                        : Mono.error(CannotCancelParcelException::new));
    }

    private Mono<ParcelEntity> findParcelById(String id) {
        return repository.findParcelById(id)
                .switchIfEmpty(Mono.error(ParcelNotFoundException::new));
    }
}