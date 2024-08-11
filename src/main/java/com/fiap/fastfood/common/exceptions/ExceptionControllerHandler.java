package com.fiap.fastfood.common.exceptions;

import com.fiap.fastfood.common.exceptions.custom.OrderCancellationException;
import com.fiap.fastfood.common.exceptions.custom.OrderCreationException;
import com.fiap.fastfood.common.exceptions.model.ExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class ExceptionControllerHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {OrderCreationException.class})
    public ResponseEntity<ExceptionDetails> resourceException(OrderCreationException ex, WebRequest request) {

        final var message = new ExceptionDetails(
                "https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/404",
                "The order could not be created.",
                ex.getCode().name(),
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getErrors());

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {OrderCancellationException.class})
    public ResponseEntity<ExceptionDetails> resourceException(OrderCancellationException ex, WebRequest request) {

        final var message = new ExceptionDetails(
                "https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/404",
                "The order could not be cancelled.",
                ex.getCode().name(),
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getErrors());

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUncaughtException(Exception ex, WebRequest request) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;

        final var message = new ExceptionDetails(
                "https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/500",
                "Internal server error. Please contact the admin.",
                ex.getClass().toString(),
                ex.getMessage(),
                status.value(),
                new Date(),
                null);

        ex.printStackTrace();

        return handleExceptionInternal(ex, message, null, status, request);
    }

}
