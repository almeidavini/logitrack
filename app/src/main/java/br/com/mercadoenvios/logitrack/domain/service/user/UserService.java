package br.com.mercadoenvios.logitrack.domain.service.user;

import br.com.mercadoenvios.logitrack.domain.exception.DatabaseIntegrationException;
import br.com.mercadoenvios.logitrack.domain.exception.UserNotFoundException;
import br.com.mercadoenvios.logitrack.domain.mapper.UserMapper;
import br.com.mercadoenvios.logitrack.domain.model.User;
import br.com.mercadoenvios.logitrack.domain.usecase.validations.user.UserValidation;
import br.com.mercadoenvios.logitrack.infrastructure.database.entity.UserEntity;
import br.com.mercadoenvios.logitrack.infrastructure.database.mapper.UserEntityMapper;
import br.com.mercadoenvios.logitrack.infrastructure.database.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Log4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserEntityMapper userEntityMapper;
    private final UserRepository userRepository;
    private final List<UserValidation> validations;

    public Mono<User> findUser(String id) {
        return userRepository.findUserById(id)
                .onErrorResume(throwable -> {
                    log.error("An error occurred while attempting to find the user to the database.", throwable);
                    return Mono.error(new DatabaseIntegrationException(throwable));
                })
                .switchIfEmpty(Mono.error(UserNotFoundException::new))
                .flatMap(userMapper::map);
    }

    public Mono<User> registerUser(User user) {
        return this.validate(user)
                .flatMap(userEntityMapper::map)
                .flatMap(this::saveUser)
                .flatMap(userMapper::map);
    }

    private Mono<UserEntity> saveUser(UserEntity user) {
        return userRepository.save(user)
                .then(Mono.defer(() -> userRepository.findUserById(user.id())))
                .onErrorResume(throwable -> {
                    log.error("An error occurred while attempting to persist the record to the database.", throwable);
                    return Mono.error(new DatabaseIntegrationException(throwable));
                });

    }

    private Mono<User> validate(User user) {
        return Flux.fromIterable(validations)
                .flatMap(validation -> validation.validate(user))
                .doOnError(throwable -> log.warn("Parcel did not meet the necessary validations for creation. Error: " + throwable.getMessage()))
                .reduce((first, second) -> second);
    }
}
