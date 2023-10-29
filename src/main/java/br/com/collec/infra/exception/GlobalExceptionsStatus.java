package br.com.collec.infra.exception;

import br.com.collec.api.payload.error.ErrorDetailsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionsStatus {

    @ExceptionHandler(ResponseStatusException.class)
    protected ResponseEntity<Object> handlerResponseStatusException(ResponseStatusException ex)
    {
        ErrorDetailsDTO error = new ErrorDetailsDTO(List.of(Objects.requireNonNull(ex.getReason())));
        return new ResponseEntity<>(error, ex.getStatusCode());
    }

}
