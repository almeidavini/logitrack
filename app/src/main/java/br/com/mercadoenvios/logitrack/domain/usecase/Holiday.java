package br.com.mercadoenvios.logitrack.domain.usecase;

import br.com.mercadoenvios.logitrack.infrastructure.integration.holiday.HolidayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class Holiday {
    private final HolidayRepository repository;

    public Mono<Boolean> isHoliday(LocalDate date, String countryCode){
        return repository.getHolidays(date.getYear(), countryCode)
                .any(holiday -> holiday.date().isEqual(date));
    }
}
