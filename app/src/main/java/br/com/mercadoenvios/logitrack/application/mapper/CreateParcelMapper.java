package br.com.mercadoenvios.logitrack.application.mapper;

import br.com.mercadoenvios.logitrack.application.dto.parcel.CreateParcelResponse;
import br.com.mercadoenvios.logitrack.domain.model.Parcel;
import br.com.mercadoenvios.logitrack.domain.model.User;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CreateParcelMapper {
    public Mono<CreateParcelResponse> map(Parcel parcel, User sender, User recipient) {
        return Mono.just(CreateParcelResponse.builder()
                .id(parcel.id())
                .description(parcel.description())
                .funFact(parcel.funFact())
                .status(parcel.status().name())
                .sender(CreateParcelResponse.User.builder()
                        .id(sender.id())
                        .name(sender.name())
                        .build())
                .recipient(CreateParcelResponse.User.builder()
                        .id(recipient.id())
                        .name(recipient.name())
                        .build())
                .delivery(CreateParcelResponse.Delivery.builder()
                        .estimatedDeliveryDate(parcel.estimatedDeliveryDate())
                        .isHoliday(parcel.isHoliday())
                        .build())
                .createdAt(parcel.createdAt())
                .updatedAt(parcel.updatedAt())
                .build());
    }
}
