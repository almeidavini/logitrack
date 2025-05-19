package br.com.mercadoenvios.logitrack.domain.usecase;

import br.com.mercadoenvios.logitrack.infrastructure.integration.dog.DogRepository;
import br.com.mercadoenvios.logitrack.infrastructure.integration.dog.dto.DogFactsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class Dog {
    private final DogRepository repository;

    public Mono<String> fetchDogFunFact() {
        return repository.getDogFact(1)
                .flatMapIterable(DogFactsResponse::data)
                .map(DogFactsResponse.DogFactData::attributes)
                .map(DogFactsResponse.DogFactAttributes::body)
                .next();
    }
}
