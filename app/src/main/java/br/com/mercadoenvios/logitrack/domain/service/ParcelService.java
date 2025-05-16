package br.com.mercadoenvios.logitrack.domain.service;

import br.com.mercadoenvios.logitrack.domain.mapper.ParcelMapper;
import br.com.mercadoenvios.logitrack.domain.model.Parcel;
import br.com.mercadoenvios.logitrack.infrastructure.database.repository.parcel.ParcelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ParcelService {

    private final ParcelRepository repository;
    private final ParcelMapper parcelMapper;

    public Mono<Parcel> createParcel(Parcel parcel) {
        return ;
    }
}
