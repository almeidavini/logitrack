package br.com.mercadoenvios.logitrack.application.mapper;

import br.com.mercadoenvios.logitrack.application.dto.parcel.CancelledParcelResponse;
import br.com.mercadoenvios.logitrack.domain.model.Parcel;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CancelledParcelMapper {
    public Mono<CancelledParcelResponse> map(Parcel parcel) {
        return Mono.just(CancelledParcelResponse.builder()
                .id(parcel.id())
                .status(parcel.status().name())
                .dataAtualizacao(parcel.updatedAt())
                .build());
    }
}

