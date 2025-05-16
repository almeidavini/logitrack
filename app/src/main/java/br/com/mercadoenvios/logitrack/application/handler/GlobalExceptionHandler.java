package br.com.mercadoenvios.logitrack.application.handler;

import br.com.mercadoenvios.logitrack.application.dto.ErrorResponse;
import br.com.mercadoenvios.logitrack.domain.exception.DatabaseIntegrationException;
import br.com.mercadoenvios.logitrack.domain.exception.UserAlreadyRegisteredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DatabaseIntegrationException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleDatabaseException(DatabaseIntegrationException ex) {
        return Mono.just(ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Failed to process request", ex.getMessage())));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleValidationException(
            WebExchangeBindException ex, ServerWebExchange exchange) {

        String details = ex.getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorResponse response = new ErrorResponse(
                "Validation error in submitted fields.",
                details
        );

        return Mono.just(ResponseEntity.badRequest().body(response));
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleUserAlreadyRegisteredException(UserAlreadyRegisteredException ex) {
        ErrorResponse response = new ErrorResponse(
                "User already registered in the system",
                null
        );
        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(response));
    }
}
