package br.com.mercadoenvios.logitrack.infrastructure.database.repository.address;

import br.com.mercadoenvios.logitrack.infrastructure.database.entity.AddressEntity;
import reactor.core.publisher.Mono;

public interface CustomAddressRepository {
    Mono<AddressEntity> findAddressByIdUser(String id);
}
