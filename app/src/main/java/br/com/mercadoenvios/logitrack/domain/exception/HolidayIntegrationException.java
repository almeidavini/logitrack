package br.com.mercadoenvios.logitrack.domain.exception;

public class HolidayIntegrationException extends RuntimeException {
    public HolidayIntegrationException(String message) {
        super("Error in holiday api integration: " + message);
    }
}
