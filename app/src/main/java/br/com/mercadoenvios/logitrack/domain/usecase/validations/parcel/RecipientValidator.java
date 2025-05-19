package br.com.mercadoenvios.logitrack.domain.usecase.validations.parcel;

import br.com.mercadoenvios.logitrack.domain.exception.validation.RecipientValidationException;
import br.com.mercadoenvios.logitrack.domain.model.Parcel;
import br.com.mercadoenvios.logitrack.domain.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RecipientValidator implements ParcelValidation {
    private final UserService userService;
    private final UserStatusValidator userStatusValidator;

    @Override
    public Mono<Parcel> validate(Parcel parcel) {
        return userService.findUser(parcel.recipientId())
                .switchIfEmpty(Mono.error(RecipientValidationException::new))
                .flatMap(userStatusValidator::validate)
                .onErrorResume(throwable -> Mono.error(new RecipientValidationException(throwable)))
                .thenReturn(parcel);
    }
}
