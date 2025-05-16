package br.com.mercadoenvios.logitrack.domain.mapper;

import br.com.mercadoenvios.logitrack.application.dto.parcel.CreateParcelRequest;
import br.com.mercadoenvios.logitrack.domain.model.Parcel;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ParcelMapper {
    public Mono<Parcel> map(CreateParcelRequest request) {
        return Mono.just(Parcel.builder()
                .description(request.description())
                .senderId(request.senderId())
                .recipientId(request.recipientId())
                .estimatedDeliveryDate(request.estimatedDeliveryDate())
                .build());
    }
}
