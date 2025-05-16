package br.com.mercadoenvios.logitrack.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientDog {
    @Value("${external-services.dog.url}")
    private String endpoint;

    @Bean("dogWebClient")
    public WebClient dogWebClient(){
        return WebClient.builder()
                .baseUrl(endpoint)
                .build();
    }
}
