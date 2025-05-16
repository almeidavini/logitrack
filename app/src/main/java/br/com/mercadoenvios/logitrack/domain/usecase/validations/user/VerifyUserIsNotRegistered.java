package br.com.mercadoenvios.logitrack.domain.usecase.validations.user;

import br.com.mercadoenvios.logitrack.domain.exception.UserAlreadyRegisteredException;
import br.com.mercadoenvios.logitrack.domain.model.User;
import br.com.mercadoenvios.logitrack.infrastructure.database.mapper.UserEntityMapper;
import br.com.mercadoenvios.logitrack.infrastructure.database.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class VerifyUserIsNotRegistered implements UserValidation {
    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public Mono<User> validate(User user) {
        return userEntityMapper.map(user)
                .flatMap(userRepository::isUserRegistered)
                .flatMap(isRegistered -> isRegistered
                        ? Mono.error(UserAlreadyRegisteredException::new)
                        : Mono.just(user)
                );
    }
}
