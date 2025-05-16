package br.com.mercadoenvios.logitrack.application.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record RegisterUserResponse(
        @JsonProperty("id")
        String id,

        @JsonProperty("name")
        String name,

        @JsonProperty("email")
        String email,

        @JsonProperty("address")
        RegisterUserAddressResponse address,

        @JsonProperty("created_at")
        LocalDateTime createdAt,

        @JsonProperty("updated_at")
        LocalDateTime updatedAt
) {
    @Builder
    public record RegisterUserAddressResponse(
            @JsonProperty("street")
            String street,

            @JsonProperty("city")
            String city,

            @JsonProperty("state")
            String state,

            @JsonProperty("zip_code")
            String zipCode,

            @JsonProperty("country")
            String country,

            @JsonProperty("created_at")
            LocalDateTime createdAt,

            @JsonProperty("updated_at")
            LocalDateTime updatedAt
    ) {
    }
}
