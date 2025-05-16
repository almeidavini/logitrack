package br.com.mercadoenvios.logitrack.application.dto.parcel;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CreateParcelRequest(
        @NotBlank
        @JsonProperty("description")
        String description,

        @NotBlank
        @JsonProperty("sender_id")
        String senderId,

        @NotBlank
        @JsonProperty("recipient_id")
        String recipientId,

        @FutureOrPresent
        @JsonProperty("estimated_delivery_date")
        LocalDate estimatedDeliveryDate
) {
}
