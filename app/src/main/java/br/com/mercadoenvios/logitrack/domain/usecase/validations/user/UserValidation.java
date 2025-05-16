package br.com.mercadoenvios.logitrack.domain.usecase.validations.user;

import br.com.mercadoenvios.logitrack.domain.model.User;
import reactor.core.publisher.Mono;

public interface UserValidation {
    Mono<User> validate(User user);
}