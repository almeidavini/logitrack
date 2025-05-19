package br.com.mercadoenvios.logitrack.application.dto.parcel;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateParcelRequest(

        @NotBlank
        @Size(max = 255)
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
