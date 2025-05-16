package br.com.mercadoenvios.logitrack.application.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record RegisterUserRequest(
        @NotBlank
        @JsonProperty("name")
        String name,

        @Email
        @NotNull
        @JsonProperty("email")
        String email,

        @Valid
        @NotNull
        @JsonProperty("address")
        AddressRequest address
) {
    @NotNull
    public record AddressRequest(
            @NotBlank
            @JsonProperty("street")
            String street,

            @NotBlank
            String number,

            @JsonProperty("city")
            String city,

            @JsonProperty("state")
            String state,

            @NotBlank
            @JsonProperty("zip_code")
            String zipCode,

            @NotBlank
            @Pattern(regexp = "^[A-Z]{2}$", message = "Country must be a valid ISO 3166-1 alpha-2 code (two uppercase letters)")
            @JsonProperty("country")
            String country
    ) {
    }
}