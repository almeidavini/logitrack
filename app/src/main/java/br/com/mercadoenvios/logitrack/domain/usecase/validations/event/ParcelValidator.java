package br.com.mercadoenvios.logitrack.domain.usecase.validations.event;

import br.com.mercadoenvios.logitrack.domain.exception.ParcelNotFoundException;
import br.com.mercadoenvios.logitrack.domain.mapper.ParcelMapper;
import br.com.mercadoenvios.logitrack.domain.model.Event;
import br.com.mercadoenvios.logitrack.domain.service.parcel.FindParcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ParcelValidator {
    private final ParcelMapper parcelMapper;
    private final FindParcelService findParcelService;

    public Mono<Void> validate(Event event){
        return parcelMapper.map(event.parcelId())
                .switchIfEmpty(Mono.error(ParcelNotFoundException::new))
                .flatMap(findParcelService::findParcel)
                .then();
    }
}
