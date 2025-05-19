package br.com.mercadoenvios.logitrack.domain.usecase.validations.parcel;

import br.com.mercadoenvios.logitrack.domain.model.Parcel;
import reactor.core.publisher.Mono;

public interface ParcelValidation {
    Mono<Parcel> validate(Parcel parcel);
}
