package br.com.mercadoenvios.logitrack.domain.facade;

import br.com.mercadoenvios.logitrack.application.dto.parcel.*;
import br.com.mercadoenvios.logitrack.application.mapper.CancelledParcelMapper;
import br.com.mercadoenvios.logitrack.application.mapper.CreateParcelMapper;
import br.com.mercadoenvios.logitrack.application.mapper.FindParcelMapper;
import br.com.mercadoenvios.logitrack.application.mapper.UpdateStatusParcelMapper;
import br.com.mercadoenvios.logitrack.domain.mapper.ParcelMapper;
import br.com.mercadoenvios.logitrack.domain.service.parcel.CancelledParcelService;
import br.com.mercadoenvios.logitrack.domain.service.parcel.CreateParcelService;
import br.com.mercadoenvios.logitrack.domain.service.parcel.FindParcelService;
import br.com.mercadoenvios.logitrack.domain.service.parcel.UpdateParcelService;
import br.com.mercadoenvios.logitrack.domain.service.event.EventService;
import br.com.mercadoenvios.logitrack.domain.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ParcelFacade {
    private final FindParcelMapper findParcelMapper;
    private final UserService userService;
    private final FindParcelService findParcelService;
    private final EventService eventService;
    private final UpdateParcelService updateParcelService;
    private final CreateParcelService parcelservice;
    private final ParcelMapper parcelMapper;
    private final CreateParcelMapper createParcelMapper;
    private final UpdateStatusParcelMapper updateStatusParcelMapper;
    private final CancelledParcelService cancelledParcelService;
    private final CancelledParcelMapper cancelledParcelMapper;

    public Mono<CreateParcelResponse> createParcel(CreateParcelRequest request) {
        return parcelMapper.map(request)
                .flatMap(parcelservice::createParcel)
                .flatMap(parcel -> Mono.zip(userService.findUser(parcel.senderId()),
                                userService.findUser(parcel.recipientId()))
                        .flatMap(tuple -> {
                            var sender = tuple.getT1();
                            var recipient = tuple.getT2();

                            return createParcelMapper.map(parcel, sender, recipient);
                        }));
    }

    public Mono<UpdateStatusParcelResponse> updateStatusParcel(String idParcel, UpdateStatusParcelRequest request) {
        return parcelMapper.map(idParcel, request)
                .flatMap(updateParcelService::updateStatusParcel)
                .flatMap(parcel -> Mono.zip(userService.findUser(parcel.senderId()),
                                userService.findUser(parcel.recipientId()))
                        .flatMap(tuple -> {
                            var sender = tuple.getT1();
                            var recipient = tuple.getT2();

                            return updateStatusParcelMapper.map(parcel, sender, recipient);
                        }));
    }

    public Mono<CancelledParcelResponse> cancelledParcel(String idParcel) {
        return parcelMapper.map(idParcel)
                .flatMap(cancelledParcelService::cancelledParcel)
                .flatMap(cancelledParcelMapper::map);
    }

    public Mono<FindParcelResponse> findParcel(String id, Boolean events) {
        return parcelMapper.map(id)
                .flatMap(findParcelService::findParcel)
                .flatMap(parcel -> Mono.zip(Mono.just(parcel),
                        userService.findUser(parcel.senderId()),
                        userService.findUser(parcel.recipientId())))
                .flatMap(tuple -> {
                    var parcel = tuple.getT1();
                    var sender = tuple.getT2();
                    var recipient = tuple.getT3();

                    return events
                            ? eventService.findEventsByParcelId(parcel.id())
                            .collectList()
                            .flatMap(listEvents -> findParcelMapper.map(parcel, sender, recipient, listEvents))
                            : findParcelMapper.map(parcel, sender, recipient);
                });
    }
}
