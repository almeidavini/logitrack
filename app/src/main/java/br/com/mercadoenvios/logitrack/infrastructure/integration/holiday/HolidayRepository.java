package br.com.mercadoenvios.logitrack.infrastructure.integration.holiday;

import br.com.mercadoenvios.logitrack.domain.exception.HolidayIntegrationException;
import br.com.mercadoenvios.logitrack.infrastructure.integration.holiday.dto.HolidayResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class HolidayRepository {
    private final WebClient client;

    public HolidayRepository(@Qualifier("holidayWebClient") WebClient client) {
        this.client = client;
    }

    public Flux<HolidayResponse> getHolidays(Integer year, String countryCode) {
        return client
                .get()
                .uri("/PublicHolidays/{year}/{countryCode}", year, countryCode)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), this::handle)
                .bodyToFlux(HolidayResponse.class);
    }

    private Mono<? extends Throwable> handle(ClientResponse response) {
        return response.bodyToMono(String.class)
                .flatMap(body -> Mono.error(new HolidayIntegrationException("Holiday API error: " + response.statusCode() + " - " + body)));
    }
}
