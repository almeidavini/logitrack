package br.com.mercadoenvios.logitrack.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientHoliday {
    @Value("${external-services.holiday.url}")
    private String endpoint;

    @Bean("holidayWebClient")
    public WebClient holidayWebClient(){
        return WebClient.builder()
                .baseUrl(endpoint)
                .build();
    }
}
