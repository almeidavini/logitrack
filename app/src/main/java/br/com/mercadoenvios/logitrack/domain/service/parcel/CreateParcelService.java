package br.com.mercadoenvios.logitrack.domain.service.parcel;

import br.com.mercadoenvios.logitrack.domain.mapper.ParcelMapper;
import br.com.mercadoenvios.logitrack.domain.model.Parcel;
import br.com.mercadoenvios.logitrack.domain.service.address.AddressService;
import br.com.mercadoenvios.logitrack.domain.usecase.Dog;
import br.com.mercadoenvios.logitrack.domain.usecase.Holiday;
import br.com.mercadoenvios.logitrack.domain.usecase.validations.parcel.ParcelValidation;
import br.com.mercadoenvios.logitrack.infrastructure.database.mapper.ParcelEntityMapper;
import br.com.mercadoenvios.logitrack.infrastructure.database.repository.parcel.ParcelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Log4j
@Service
@RequiredArgsConstructor
public class CreateParcelService {
    private final Dog dog;
    private final Holiday holiday;
    private final ParcelRepository repository;
    private final ParcelMapper parcelMapper;
    private final ParcelEntityMapper parcelEntityMapper;
    private final List<ParcelValidation> validations;
    private final AddressService addressService;

    public Mono<Parcel> createParcel(Parcel parcel) {
        return this.validate(parcel)
                .flatMap(this::enrichIsHoliday)
                .flatMap(this::enrichDogFunFact)
                .flatMap(parcelEntityMapper::map)
                .flatMap(repository::save)
                .flatMap(parcelEntity -> repository.findParcelById(parcelEntity.id()))
                .flatMap(parcelMapper::map);
    }

    private Mono<Parcel> validate(Parcel parcel) {
        return Flux.fromIterable(validations)
                .flatMap(validation -> validation.validate(parcel))
                .doOnError(throwable -> log.warn("Parcel did not meet the necessary validations for creation."))
                .reduce((first, second) -> second);
    }

    private Mono<Parcel> enrichIsHoliday(Parcel parcel) {
        return addressService.findAddressByIdUser(parcel.recipientId())
                .flatMap(address -> holiday.isHoliday(parcel.estimatedDeliveryDate(), address.country()))
                .flatMap(isHoliday -> Mono.just(parcel.with(Parcel::isHoliday, isHoliday)));
    }

    private Mono<Parcel> enrichDogFunFact(Parcel parcel) {
        return dog.fetchDogFunFact()
                .flatMap(fact -> Mono.just(parcel.with(Parcel::funFact, fact)));
    }
}
