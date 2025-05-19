package br.com.mercadoenvios.logitrack.domain.exception.validation;

public class SameSenderAndRecipientException extends ValidationException {
    public SameSenderAndRecipientException() {
        super("Sender user cannot be the same as recipient");
    }
}
