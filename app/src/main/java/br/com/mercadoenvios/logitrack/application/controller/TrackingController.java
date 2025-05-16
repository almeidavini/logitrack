package br.com.mercadoenvios.logitrack.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("logitrack/api/trackings")
@RequiredArgsConstructor
public class TrackingController {

    @GetMapping("/parcels/{id}/history")
    public Mono<Void> getTrackingHistory() {
        return Mono.empty();
    }

    @GetMapping("/parcels/{id}")
    public Mono<Void> getCurrentParcelStatus() {
        return Mono.empty();
    }

    @PostMapping("/parcels/{id}")
    public Mono<Void> createTrackingEvent() {
        return Mono.empty();
    }
}
