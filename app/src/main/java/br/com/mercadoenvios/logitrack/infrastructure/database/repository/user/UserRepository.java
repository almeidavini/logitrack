package br.com.mercadoenvios.logitrack.infrastructure.database.repository.user;

import br.com.mercadoenvios.logitrack.infrastructure.database.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, String>, CustomUserRepository {
}
