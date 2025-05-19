package br.com.mercadoenvios.logitrack.domain.exception;

public class ParcelNotFoundException extends RuntimeException {
    public ParcelNotFoundException(){
        super("Parcel is not already registered in the system");
    }
}
