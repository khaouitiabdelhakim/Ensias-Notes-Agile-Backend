package com.ensias.ensiasnote.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Represents an error message with status code, timestamp, message, and description.
 */
@Setter
@Getter
@AllArgsConstructor
public class ErrorMessage {
    private int statusCode;      // HTTP status code
    private Date timestamp;      // Timestamp of the error
    private String message;      // Error message
    private String description;  // Additional description of the error
}
