package br.com.mercadoenvios.logitrack.domain.exception.validation;

public class SenderValidationException extends ValidationException {
    public SenderValidationException(Throwable cause) {
        super("Failed to validate sender user : " + cause.getMessage(), cause);
    }

    public SenderValidationException() {
        super("Failed to validate sender user.");
    }
}
