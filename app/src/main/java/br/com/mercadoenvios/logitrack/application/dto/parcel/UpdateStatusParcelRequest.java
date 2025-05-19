package br.com.mercadoenvios.logitrack.application.dto.parcel;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record UpdateStatusParcelRequest(
        @NotBlank
        @JsonProperty("status")
        String status
) {
}
