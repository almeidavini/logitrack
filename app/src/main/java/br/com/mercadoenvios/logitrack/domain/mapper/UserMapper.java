package br.com.mercadoenvios.logitrack.domain.mapper;

import br.com.mercadoenvios.logitrack.application.dto.user.RegisterUserRequest;
import br.com.mercadoenvios.logitrack.domain.model.User;
import br.com.mercadoenvios.logitrack.infrastructure.database.entity.UserEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class UserMapper {

    public Mono<User> map(RegisterUserRequest request) {
        return Mono.just(User.builder()
                .id(UUID.randomUUID().toString())
                .name(request.name())
                .email(request.email())
                .build());
    }

    public Mono<User> map(UserEntity userEntity) {
        return Mono.just(User.builder()
                .id(userEntity.id())
                .name(userEntity.name())
                .email(userEntity.email())
                .status(userEntity.status())
                .createdAt(userEntity.createdAt())
                .updatedAt(userEntity.updatedAt())
                .build());
    }
}
