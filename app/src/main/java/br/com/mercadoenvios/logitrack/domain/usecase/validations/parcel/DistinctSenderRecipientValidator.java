package br.com.mercadoenvios.logitrack.domain.usecase.validations.parcel;

import br.com.mercadoenvios.logitrack.domain.exception.validation.SameSenderAndRecipientException;
import br.com.mercadoenvios.logitrack.domain.model.Parcel;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DistinctSenderRecipientValidator implements ParcelValidation {
    @Override
    public Mono<Parcel> validate(Parcel parcel) {
        return parcel.recipientId().equals(parcel.senderId()) ? Mono.error(SameSenderAndRecipientException::new) : Mono.empty();
    }
}
