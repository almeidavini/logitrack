package br.com.mercadoenvios.logitrack.application.dto.parcel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record CreateParcelResponse(
        @JsonProperty("id")
        String id,

        @JsonProperty("description")
        String description,

        @JsonProperty("fun_fact")
        String funFact,

        @JsonProperty("status")
        String status,

        @JsonProperty("sender")
        User sender,

        @JsonProperty("recipient")
        User recipient,

        @JsonProperty("delivery")
        Delivery delivery,

        @JsonProperty("created_at")
        LocalDateTime createdAt,

        @JsonProperty("updated_at")
        LocalDateTime updatedAt
) {
    @Builder
    public record User(
            @JsonProperty("id")
            String id,

            @JsonProperty("name")
            String name
    ) {
    }

    @Builder
    public record Delivery(
            @JsonProperty("estimated_delivery_date")
            LocalDate estimatedDeliveryDate,

            @JsonProperty("is_holiday")
            Boolean isHoliday
    ) {
    }
}
