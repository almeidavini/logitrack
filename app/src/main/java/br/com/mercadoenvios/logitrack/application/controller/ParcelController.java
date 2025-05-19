package br.com.mercadoenvios.logitrack.application.controller;

import br.com.mercadoenvios.logitrack.application.dto.parcel.*;
import br.com.mercadoenvios.logitrack.domain.exception.InvalidEventsHeaderException;
import br.com.mercadoenvios.logitrack.domain.facade.ParcelFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("logitrack/api/parcels")
@RequiredArgsConstructor
public class ParcelController {

    private final ParcelFacade service;

    @PostMapping
    public Mono<CreateParcelResponse> createParcel(@RequestBody @Valid CreateParcelRequest parcel) {
        return service.createParcel(parcel);
    }

    @PatchMapping("/{id}")
    public Mono<UpdateStatusParcelResponse> updateParcel(@PathVariable String id, @RequestBody @Valid UpdateStatusParcelRequest request) {
        return service.updateStatusParcel(id, request);
    }

    @DeleteMapping("/{id}")
    public Mono<CancelledParcelResponse> cancelShipping(@PathVariable String id) {
        return service.cancelledParcel(id);
    }

    @GetMapping("/{id}")
    public Mono<FindParcelResponse> getParcel(@PathVariable String id, @RequestHeader(value = "events", required = false, defaultValue = "false") String events) {
        if (!events.equalsIgnoreCase("true") && !events.equalsIgnoreCase("false")) {
            throw new InvalidEventsHeaderException(events);
        }

        return service.findParcel(id, Boolean.parseBoolean(events));
    }
}
