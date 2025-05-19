package br.com.mercadoenvios.logitrack.application.dto.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record FindEventResponse(
        @JsonProperty("id")
        String id,

        @JsonProperty("parcel_id")
        String parcelId,

        @JsonProperty("location")
        String location,

        @JsonProperty("description")
        String description,

        @JsonProperty("created_at")
        LocalDateTime createdAt
) {
}
