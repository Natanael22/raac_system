package br.com.raac_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class TISSIntegrationException extends RuntimeException {
    public TISSIntegrationException(String message) {
        super(message);
    }
}
