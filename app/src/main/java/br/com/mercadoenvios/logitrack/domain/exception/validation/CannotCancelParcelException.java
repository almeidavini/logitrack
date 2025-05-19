package br.com.mercadoenvios.logitrack.domain.exception.validation;

public class CannotCancelParcelException extends ValidationException {
    public CannotCancelParcelException() {
        super("Cannot cancel parcel. It has already been shipped or delivered.");
    }
}
