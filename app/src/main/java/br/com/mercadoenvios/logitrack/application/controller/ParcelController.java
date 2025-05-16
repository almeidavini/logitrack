package br.com.mercadoenvios.logitrack.application.controller;

import br.com.mercadoenvios.logitrack.domain.model.Parcel;
import br.com.mercadoenvios.logitrack.domain.service.ParcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("logitrack/api/parcels")
@RequiredArgsConstructor
public class ParcelController {

    private final ParcelService service;

    @PostMapping
    public Mono<Boolean> createParcel(@RequestBody Parcel parcel) {
        return Mono.empty();
    }

    @PatchMapping
    public Mono<Parcel> updateParcel() {
        return Mono.empty();
    }

    @DeleteMapping("/{id}")
    public Mono<Void> cancelShipping(@PathVariable String id) {
        return Mono.empty();
    }

    @GetMapping("/{id}")
    public Mono<Parcel> getParcel(@PathVariable String id) {
        return Mono.empty();
    }

    @GetMapping()
    public Mono<Parcel> getParcels(@RequestParam String senderId, @RequestParam Long recipientId) {
        return Mono.empty();
    }
}
