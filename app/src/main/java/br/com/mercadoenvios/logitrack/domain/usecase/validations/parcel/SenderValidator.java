package br.com.mercadoenvios.logitrack.domain.usecase.validations.parcel;

import br.com.mercadoenvios.logitrack.domain.exception.validation.SenderValidationException;
import br.com.mercadoenvios.logitrack.domain.model.Parcel;
import br.com.mercadoenvios.logitrack.domain.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SenderValidator implements ParcelValidation {
    private final UserService userService;
    private final UserStatusValidator userStatusValidator;

    @Override
    public Mono<Parcel> validate(Parcel parcel) {
        return userService.findUser(parcel.senderId())
                .switchIfEmpty(Mono.error(SenderValidationException::new))
                .flatMap(userStatusValidator::validate)
                .onErrorResume(throwable -> Mono.error(new SenderValidationException(throwable)))
                .thenReturn(parcel);
    }
}
