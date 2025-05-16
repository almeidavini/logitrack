package br.com.mercadoenvios.logitrack.infrastructure.database.repository.user;

import br.com.mercadoenvios.logitrack.infrastructure.database.entity.UserEntity;
import reactor.core.publisher.Mono;

public interface CustomUserRepository {
    Mono<Boolean> isUserRegistered(UserEntity user);
    Mono<UserEntity> findUserById(String id);
}
