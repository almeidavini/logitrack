package br.com.mercadoenvios.logitrack.domain.service.event;

import br.com.mercadoenvios.logitrack.domain.mapper.EventMapper;
import br.com.mercadoenvios.logitrack.domain.model.Event;
import br.com.mercadoenvios.logitrack.domain.usecase.validations.event.ParcelValidator;
import br.com.mercadoenvios.logitrack.infrastructure.database.mapper.EventEntityMapper;
import br.com.mercadoenvios.logitrack.infrastructure.database.repository.event.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EventService {
    private final ParcelValidator validator;
    private final EventMapper mapper;
    private final EventEntityMapper eventEntityMapper;
    private final EventRepository repository;

    public Flux<Event> findEventsByParcelId(String id) {
        return repository.findEventsByParcelId(id)
                .flatMap(mapper::map);
    }

    public Mono<Event> createEvent(Event event) {
        return validator.validate(event)
                .then(Mono.defer(() -> repository.save(eventEntityMapper
                        .map(event))))
                .flatMap(eventEntity -> repository.findEventById(eventEntity.id()))
                .flatMap(mapper::map);
    }
}
