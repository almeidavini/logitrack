package br.com.mercadoenvios.logitrack.domain.exception.validation;

public class UserAlreadyRegisteredException extends RuntimeException {
    public UserAlreadyRegisteredException() {
        super("User is already registered in the system");
    }
}
