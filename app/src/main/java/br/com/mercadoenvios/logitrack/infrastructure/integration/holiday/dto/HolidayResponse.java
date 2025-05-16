package br.com.mercadoenvios.logitrack.infrastructure.integration.holiday.dto;

import java.time.LocalDate;
import java.util.List;

public record HolidayResponse(
        LocalDate date,
        String localName,
        String name,
        String countryCode,
        boolean fixed,
        boolean global,
        List<String> counties,
        Integer launchYear,
        List<String> types
) {
}