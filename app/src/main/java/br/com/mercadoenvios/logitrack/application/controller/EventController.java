package br.com.mercadoenvios.logitrack.application.controller;

import br.com.mercadoenvios.logitrack.application.dto.event.CreateEventRequest;
import br.com.mercadoenvios.logitrack.application.dto.event.FindEventResponse;
import br.com.mercadoenvios.logitrack.domain.facade.EventFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("logitrack/api/events")
@RequiredArgsConstructor
public class EventController {
    private final EventFacade service;

    @GetMapping("/parcels/{id}/history")
    public Flux<FindEventResponse> getEventsHistory(@PathVariable String id) {
        return service.findEvents(id);
    }

    @PostMapping
    public Mono<FindEventResponse> createEvent(@RequestBody @Valid CreateEventRequest request) {
        return service.createEvent(request);
    }
}
