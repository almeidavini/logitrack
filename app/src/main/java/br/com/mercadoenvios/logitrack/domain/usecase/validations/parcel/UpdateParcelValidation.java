package br.com.mercadoenvios.logitrack.domain.usecase.validations.parcel;

import br.com.mercadoenvios.logitrack.domain.exception.ParcelNotFoundException;
import br.com.mercadoenvios.logitrack.domain.exception.validation.InvalidParcelStatusTransitionException;
import br.com.mercadoenvios.logitrack.domain.model.Parcel;
import br.com.mercadoenvios.logitrack.infrastructure.database.entity.ParcelEntity;
import br.com.mercadoenvios.logitrack.infrastructure.database.repository.parcel.ParcelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UpdateParcelValidation {
    private final ParcelRepository repository;

    public Mono<Parcel> validate(Parcel parcel) {
        return findParcelById(parcel.id())
                .flatMap(parcelEntity -> Parcel.Status.valueOf(parcelEntity.status()).canTransitionTo(parcel.status())
                        ? Mono.just(parcel)
                        : Mono.error(InvalidParcelStatusTransitionException::new));
    }

    private Mono<ParcelEntity> findParcelById(String id) {
        return repository.findParcelById(id)
                .switchIfEmpty(Mono.error(ParcelNotFoundException::new));
    }
}
