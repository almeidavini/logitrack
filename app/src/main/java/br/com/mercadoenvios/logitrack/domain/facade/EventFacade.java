package br.com.mercadoenvios.logitrack.domain.facade;

import br.com.mercadoenvios.logitrack.application.dto.event.CreateEventRequest;
import br.com.mercadoenvios.logitrack.application.dto.event.FindEventResponse;
import br.com.mercadoenvios.logitrack.application.mapper.FindEventsMapper;
import br.com.mercadoenvios.logitrack.domain.exception.ParcelEventsNotFoundException;
import br.com.mercadoenvios.logitrack.domain.mapper.EventMapper;
import br.com.mercadoenvios.logitrack.domain.service.event.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EventFacade {
    private final EventService service;
    private final EventMapper eventMapper;
    private final FindEventsMapper findEventsMapper;

    public Flux<FindEventResponse> findEvents(String parcelId) {
        return service.findEventsByParcelId(parcelId)
                .switchIfEmpty(Mono.error(ParcelEventsNotFoundException::new))
                .flatMap(findEventsMapper::map);
    }

    public Mono<FindEventResponse> createEvent(CreateEventRequest event) {
        return eventMapper.map(event)
                .flatMap(service::createEvent)
                .flatMap(findEventsMapper::map);
    }
}
