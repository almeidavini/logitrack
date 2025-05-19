package br.com.mercadoenvios.logitrack.infrastructure.database.repository.event;

import br.com.mercadoenvios.logitrack.infrastructure.database.entity.EventEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomEventRepository {
    Flux<EventEntity> findEventsByParcelId(String id);

    Mono<EventEntity> findEventById(String id);
}
