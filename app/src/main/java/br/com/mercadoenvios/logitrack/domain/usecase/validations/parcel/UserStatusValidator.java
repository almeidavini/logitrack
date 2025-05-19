package br.com.mercadoenvios.logitrack.domain.usecase.validations.parcel;

import br.com.mercadoenvios.logitrack.domain.exception.validation.InactiveUserException;
import br.com.mercadoenvios.logitrack.domain.model.User;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UserStatusValidator {
    public Mono<Void> validate(User user) {
        return user.status().equals(User.Status.ACTIVE) ? Mono.empty() : Mono.error(InactiveUserException::new);
    }
}
