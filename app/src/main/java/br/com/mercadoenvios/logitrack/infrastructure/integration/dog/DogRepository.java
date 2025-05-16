package br.com.mercadoenvios.logitrack.infrastructure.integration.dog;

import br.com.mercadoenvios.logitrack.infrastructure.integration.dog.dto.DogFactsResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DogRepository {
    private final WebClient client;

    public DogRepository(@Qualifier("dogWebClient") WebClient client) {
        this.client = client;
    }

    public Flux<DogFactsResponse> getDogFact(Integer year, String countryCode) {
        return client
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/facts")
                        .queryParam("limit", 1)
                        .build())
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), this::handle)
                .bodyToFlux(DogFactsResponse.class);
    }

    private Mono<? extends Throwable> handle(ClientResponse response) {
        return Mono.error(RuntimeException::new);
    }
}
