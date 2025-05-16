package br.com.mercadoenvios.logitrack.infrastructure.database.repository.parcel;

import br.com.mercadoenvios.logitrack.infrastructure.database.entity.ParcelEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ParcelRepository extends ReactiveCrudRepository<ParcelEntity, String> {
}
