package br.com.mercadoenvios.logitrack.domain.facade;

import br.com.mercadoenvios.logitrack.application.dto.parcel.CreateParcelRequest;
import br.com.mercadoenvios.logitrack.domain.mapper.ParcelMapper;
import br.com.mercadoenvios.logitrack.domain.service.ParcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ParcelFacade {
    private final ParcelService service;
    private final ParcelMapper parcelMapper;

    public Mono<Void> createParcel(CreateParcelRequest request){
        return parcelMapper.map(request)
                .flatMap(service::createParcel);
    }
}
