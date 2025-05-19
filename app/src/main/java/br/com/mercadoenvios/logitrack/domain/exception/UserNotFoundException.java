package br.com.mercadoenvios.logitrack.domain.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User is not already registered in the system");
    }
}
