package br.com.mercadoenvios.logitrack.domain.exception.validation;

public class RecipientValidationException extends ValidationException {
    public RecipientValidationException(Throwable cause) {
        super("Failed to validate recipient user : " + cause.getMessage(), cause);
    }

    public RecipientValidationException() {
        super("Failed to validate recipient user.");
    }
}
