package br.com.SistemaControleInsumos.exceptions;

import br.com.SistemaControleInsumos.exceptions.dto.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exceptionGeneric(final Exception ex,
                                              final HttpServletRequest request) {

        var contet = ExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(500)
                .error(ex.getMessage())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(contet.status()).body(contet);
    }


    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> responseStatusException(final ResponseStatusException ex,
                                                     final HttpServletRequest request) {
        var contet = ExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(ex.getStatusCode().value())
                .error(ex.getReason())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(contet.status()).body(contet);

    }

    @ExceptionHandler(UserExists.class)
    public ResponseEntity<?> exceptionGeneric(final UserExists ex,
                                              final HttpServletRequest request) {

        var contet = ExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error("conflict")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(contet.status()).body(contet);
    }

}
