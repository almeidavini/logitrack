package br.com.mercadoenvios.logitrack.application.dto.parcel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record FindParcelResponse(
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
        LocalDateTime deliveredAt,

        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        @JsonProperty("events")
        List<Event> events
) {
    public record Event(
            @JsonProperty("location")
            String location,

            @JsonProperty("description")
            String description,

            @JsonProperty("created_at")
            LocalDateTime createdAt
    ) {
    }
}
