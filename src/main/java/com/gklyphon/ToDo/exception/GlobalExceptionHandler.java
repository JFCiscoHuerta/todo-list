package com.gklyphon.ToDo.exception;

import com.gklyphon.ToDo.exception.custom.ElementNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler that intercepts exceptions thrown by controllers and processes them consistently.
 * <p>
 * This class provides centralized exception handling for the application, ensuring that appropriate
 * responses are returned with meaningful error messages and correct HTTP status codes.
 * </p>
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 28-Oct-2024
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link ElementNotFoundException} when a requested element is not found.
     * <p>Logs the error and returns a 404 (Not Found) response.</p>
     *
     * @param ex the exception indicating that the element was not found
     * @return a {@code ResponseEntity} containing an {@link ErrorResponse} with the error details
     */
    @ExceptionHandler(ElementNotFoundException.class)
    public ResponseEntity<?> handleElementNotFoundException(ElementNotFoundException ex) {
        log.error("Element not found: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Handles generic {@link Exception} that are not explicitly caught elsewhere.
     * <p>Logs the error and returns a 500 (Internal Server Error) response.</p>
     *
     * @param ex the exception that was not specifically handled
     * @return a {@code ResponseEntity} containing an {@link ErrorResponse} with the error details
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        log.error("Unexpected error occurred", ex);
        ErrorResponse errorResponse = new ErrorResponse("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    /**
     * Handles {@link DataAccessException} thrown during database operations.
     * <p>Logs the error and returns a 500 (Internal Server Error) response.</p>
     *
     * @param ex the exception indicating a database access error
     * @return a {@code ResponseEntity} containing an {@link ErrorResponse} with the error details
     */
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<?> handleDataAccessException(DataAccessException ex) {
        log.error("Error retrieving tasks: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("Error Retrieving Tasks", HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

}
