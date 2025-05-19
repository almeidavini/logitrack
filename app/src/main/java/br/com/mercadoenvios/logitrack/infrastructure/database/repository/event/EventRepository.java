package br.com.mercadoenvios.logitrack.infrastructure.database.repository.event;

import br.com.mercadoenvios.logitrack.infrastructure.database.entity.EventEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EventRepository extends ReactiveCrudRepository<EventEntity, String>, CustomEventRepository {
}
