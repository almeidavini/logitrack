package br.com.mercadoenvios.logitrack.domain.mapper;

import br.com.mercadoenvios.logitrack.application.dto.user.RegisterUserRequest;
import br.com.mercadoenvios.logitrack.domain.model.Address;
import br.com.mercadoenvios.logitrack.domain.model.User;
import br.com.mercadoenvios.logitrack.infrastructure.database.entity.AddressEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class AddressMapper {

    public Mono<Address> map(RegisterUserRequest request, User user) {
        return Mono.just(Address.builder()
                .userId(user.id())
                .street(request.address().street())
                .number(request.address().number())
                .city(request.address().city())
                .state(request.address().state())
                .zipCode(request.address().zipCode())
                .country(request.address().country())
                .build());
    }

    public Mono<Address> map(AddressEntity address) {
        return Mono.just(Address.builder()
                .userId(address.userId())
                .street(address.street())
                .number(address.number())
                .city(address.city())
                .state(address.state())
                .zipCode(address.zipCode())
                .country(address.country())
                .createdAt(address.createdAt())
                .updatedAt(address.updatedAt())
                .build());
    }
}