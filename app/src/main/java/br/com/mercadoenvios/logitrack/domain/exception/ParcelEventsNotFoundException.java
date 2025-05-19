package br.com.mercadoenvios.logitrack.domain.exception;

public class ParcelEventsNotFoundException extends RuntimeException {
    public ParcelEventsNotFoundException(){
        super("No events found for the specified parcel.");
    }
}
