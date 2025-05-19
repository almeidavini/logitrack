package br.com.mercadoenvios.logitrack.application.dto.parcel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UpdateStatusParcelResponse(
        @JsonProperty("id")
        String id,

        @JsonProperty("description")
        String description,

        @JsonProperty("sender")
        String sender,

        @JsonProperty("recipient")
        String recipient,

        @JsonProperty("status")
        String status,

        @JsonProperty("created_at")
        LocalDateTime createdAt,

        @JsonProperty("updated_at")
        LocalDateTime updatedAt,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("delivered_at")
        LocalDateTime deliveredAt
) {
}
