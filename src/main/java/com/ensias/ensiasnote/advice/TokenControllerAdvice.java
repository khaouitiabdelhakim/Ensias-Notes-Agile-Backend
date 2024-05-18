package com.ensias.ensiasnote.advice;

import com.ensias.ensiasnote.exception.TokenRefreshException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/**
 * Global controller advice for handling token-related exceptions.
 */
@RestControllerAdvice
public class TokenControllerAdvice {

    /**
     * Handles TokenRefreshException and returns an ErrorMessage with details.
     *
     * @param ex      The TokenRefreshException instance
     * @param request The WebRequest object
     * @return An ErrorMessage object with error details
     */
    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.FORBIDDEN.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
    }
}
