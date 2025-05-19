package br.com.mercadoenvios.logitrack.application.mapper;

import br.com.mercadoenvios.logitrack.application.dto.event.FindEventResponse;
import br.com.mercadoenvios.logitrack.domain.model.Event;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class FindEventsMapper {
    public Mono<FindEventResponse> map(Event event) {
        return Mono.just(FindEventResponse.builder()
                .id(event.id())
                .parcelId(event.parcelId())
                .description(event.description())
                .location(event.location())
                .createdAt(event.createdAt())
                .build());
    }
}
