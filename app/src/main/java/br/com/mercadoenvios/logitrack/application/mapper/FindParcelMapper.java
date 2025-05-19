package br.com.mercadoenvios.logitrack.application.mapper;

import br.com.mercadoenvios.logitrack.application.dto.parcel.FindParcelResponse;
import br.com.mercadoenvios.logitrack.domain.model.Parcel;
import br.com.mercadoenvios.logitrack.domain.model.Event;
import br.com.mercadoenvios.logitrack.domain.model.User;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FindParcelMapper {
    public Mono<FindParcelResponse> map(Parcel parcel, User sender, User recipient, List<Event> parcelEvents) {
        List<FindParcelResponse.Event> events = Optional.ofNullable(parcelEvents)
                .map(list -> list.stream()
                        .map(event -> new FindParcelResponse.Event(
                                event.location(),
                                event.description(),
                                event.createdAt()
                        ))
                        .collect(Collectors.toList()))
                .orElse(null);

        return Mono.just(FindParcelResponse.builder()
                .id(parcel.id())
                .description(parcel.description())
                .sender(sender.name())
                .recipient(recipient.name())
                .status(parcel.status().name())
                .createdAt(parcel.createdAt())
                .updatedAt(parcel.updatedAt())
                .deliveredAt(parcel.deliveredAt())
                .events(events)
                .build());
    }

    public Mono<? extends FindParcelResponse> map(Parcel parcel, User sender, User recipient) {
        return Mono.just(FindParcelResponse.builder()
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
