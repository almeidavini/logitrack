package br.com.mercadoenvios.logitrack.application.mapper;

import br.com.mercadoenvios.logitrack.application.dto.parcel.UpdateStatusParcelResponse;
import br.com.mercadoenvios.logitrack.domain.model.Parcel;
import br.com.mercadoenvios.logitrack.domain.model.User;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UpdateStatusParcelMapper {
    public Mono<UpdateStatusParcelResponse> map(Parcel parcel, User sender, User recipient) {
        return Mono.just(UpdateStatusParcelResponse.builder()
                .id(parcel.id())
                .description(parcel.description())
                .sender(sender.name())
                .recipient(recipient.name())
                .status(parcel.status().name())
                .createdAt(parcel.createdAt())
                .updatedAt(parcel.updatedAt())
                .deliveredAt(parcel.deliveredAt())
                .build());
    }
}
