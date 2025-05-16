package br.com.mercadoenvios.logitrack.application.dto;

public record ErrorResponse(
        String error,
        String details
) {}