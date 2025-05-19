package br.com.mercadoenvios.logitrack.domain.exception.validation;

public class InactiveUserException extends ValidationException {
    public InactiveUserException() {
        super("User is inactive on the system.");
    }
}