package br.com.mercadoenvios.logitrack.application.mapper;

import br.com.mercadoenvios.logitrack.application.dto.user.RegisterUserResponse;
import br.com.mercadoenvios.logitrack.domain.model.Address;
import br.com.mercadoenvios.logitrack.domain.model.User;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class RegisterUserMapper {
    public Mono<RegisterUserResponse> map(User user, Address address) {
        return Mono.just(RegisterUserResponse.builder()
                .id(user.id())
                .name(user.name())
                .email(user.email())
                .address(RegisterUserResponse.RegisterUserAddressResponse.builder()
                        .street(address.street())
                        .city(address.city())
                        .state(address.state())
                        .zipCode(address.zipCode())
                        .country(address.country())
                        .createdAt(address.createdAt())
                        .updatedAt(address.updatedAt())
                        .build())
                .createdAt(user.createdAt())
                .updatedAt(user.updatedAt())
                .build());
    }
}
