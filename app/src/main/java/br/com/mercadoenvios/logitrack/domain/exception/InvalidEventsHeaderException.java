package br.com.mercadoenvios.logitrack.domain.exception;

public class InvalidEventsHeaderException extends RuntimeException {
    public InvalidEventsHeaderException(String value){
        super("Invalid value '" + value + "' for header 'events'. Must be 'true' or 'false'.");
    }
}
