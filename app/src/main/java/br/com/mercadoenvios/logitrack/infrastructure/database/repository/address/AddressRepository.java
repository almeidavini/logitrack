package br.com.mercadoenvios.logitrack.infrastructure.database.repository.address;

import br.com.mercadoenvios.logitrack.infrastructure.database.entity.AddressEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AddressRepository extends ReactiveCrudRepository<AddressEntity, String>, CustomAddressRepository {
}
