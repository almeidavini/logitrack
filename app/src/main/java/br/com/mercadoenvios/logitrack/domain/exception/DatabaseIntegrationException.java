package br.com.mercadoenvios.logitrack.domain.exception;

public class DatabaseIntegrationException extends RuntimeException {

    public DatabaseIntegrationException(String message) {
        super(message);
    }

    public DatabaseIntegrationException(Throwable cause) {
        super("Error in database integration: " + cause.getMessage(), cause);
    }
}