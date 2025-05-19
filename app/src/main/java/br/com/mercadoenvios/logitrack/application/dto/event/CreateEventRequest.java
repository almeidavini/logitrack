package br.com.mercadoenvios.logitrack.application.dto.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateEventRequest(
        @NotBlank
        @JsonProperty("parcel_id")
        String parcelId,

        @NotBlank
        @JsonProperty("location")
        String location,

        @JsonProperty("description")
        String description
) {
}
