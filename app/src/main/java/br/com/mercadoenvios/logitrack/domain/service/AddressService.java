package br.com.mercadoenvios.logitrack.domain.service;

import br.com.mercadoenvios.logitrack.domain.exception.DatabaseIntegrationException;
import br.com.mercadoenvios.logitrack.domain.mapper.AddressMapper;
import br.com.mercadoenvios.logitrack.domain.model.Address;
import br.com.mercadoenvios.logitrack.domain.model.User;
import br.com.mercadoenvios.logitrack.infrastructure.database.entity.AddressEntity;
import br.com.mercadoenvios.logitrack.infrastructure.database.mapper.AddressEntityMapper;
import br.com.mercadoenvios.logitrack.infrastructure.database.repository.address.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressMapper addressMapper;
    private final AddressEntityMapper addressEntityMapper;
    private final AddressRepository addressRepository;

    public Mono<Address> registerAddress(Address address, User user) {
        return addressEntityMapper.map(address, user)
                .flatMap(this::saveAddress)
                .flatMap(addressMapper::map);
    }

    public Mono<Address> findAddressByIdUser(String id) {
        return addressRepository.findAddressByIdUser(id)
                .switchIfEmpty(Mono.error(RuntimeException::new))
                .flatMap(addressMapper::map);
    }

    private Mono<AddressEntity> saveAddress(AddressEntity address) {
        return addressRepository.save(address)
                .then(Mono.defer(() -> addressRepository.findAddressByIdUser(address.userId())))
                .onErrorResume(throwable -> {
                    log.error("An error occurred while attempting to persist the record to the database.", throwable);
                    return Mono.error(new DatabaseIntegrationException(throwable));
                });

    }
}