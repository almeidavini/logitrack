package br.com.mercadoenvios.logitrack.application.dto.parcel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CancelledParcelResponse(
        @JsonProperty("id")
        String id,

        @JsonProperty("status")
        String status,

        @JsonProperty("data_atualizacao")
        LocalDateTime dataAtualizacao
) {
}
