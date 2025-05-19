package br.com.mercadoenvios.logitrack.domain.mapper;

import br.com.mercadoenvios.logitrack.application.dto.event.CreateEventRequest;
import br.com.mercadoenvios.logitrack.domain.model.Event;
import br.com.mercadoenvios.logitrack.infrastructure.database.entity.EventEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class EventMapper {
    public Mono<Event> map(EventEntity eventEntity) {
        return Mono.just(Event.builder()
                .id(eventEntity.id())
                .parcelId(eventEntity.parcelId())
                .location(eventEntity.location())
                .description(eventEntity.description())
                .createdAt(eventEntity.createdAt())
                .build());
    }

    public Mono<Event> map(CreateEventRequest request) {
        return Mono.just(Event.builder()
                .parcelId(request.parcelId())
                .location(request.location())
                .description(request.description())
                .build());
    }
}
