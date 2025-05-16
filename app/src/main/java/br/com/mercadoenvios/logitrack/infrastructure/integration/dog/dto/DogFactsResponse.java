package br.com.mercadoenvios.logitrack.infrastructure.integration.dog.dto;

import java.util.List;

public record DogFactsResponse(
        List<DogFactData> data
) {
    public record DogFactData(
            String id,
            String type,
            DogFactAttributes attributes
    ) {
    }

    public record DogFactAttributes(
            String body
    ) {
    }
}