package br.com.mercadoenvios.logitrack.domain.service.parcel;

import br.com.mercadoenvios.logitrack.domain.exception.ParcelNotFoundException;
import br.com.mercadoenvios.logitrack.domain.mapper.ParcelMapper;
import br.com.mercadoenvios.logitrack.domain.model.Parcel;
import br.com.mercadoenvios.logitrack.infrastructure.database.entity.ParcelEntity;
import br.com.mercadoenvios.logitrack.infrastructure.database.mapper.ParcelEntityMapper;
import br.com.mercadoenvios.logitrack.infrastructure.database.repository.parcel.ParcelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FindParcelService {
    private final ParcelMapper parcelMapper;
    private final ParcelRepository repository;
    private final ParcelEntityMapper parcelEntityMapper;

    public Mono<Parcel> findParcel(Parcel parcel) {
        return parcelEntityMapper.map(parcel)
                .flatMap(parcelEntity -> findParcelById(parcelEntity.id()))
                .flatMap(parcelMapper::map);
    }

    private Mono<ParcelEntity> findParcelById(String id) {
        return repository.findParcelById(id)
                .switchIfEmpty(Mono.error(ParcelNotFoundException::new));
    }
}
