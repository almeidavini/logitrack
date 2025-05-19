package br.com.mercadoenvios.logitrack.domain.exception.validation;

public class InvalidParcelStatusTransitionException extends ValidationException {
    public InvalidParcelStatusTransitionException(){
        super("Parcel status transition is not allowed");
    }
}
