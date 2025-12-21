package com.kerem.exception;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.NotAcceptableStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    /*
     * Code,Constant,Meaning,When to use
     * 200,HttpStatus.OK,OK,Standard successful GET/PUT.
     * 201,HttpStatus.CREATED,Created,Successful POST (creation).
     * 204,HttpStatus.NO_CONTENT,No Content,Successful DELETE (nothing to return).
     * 400,HttpStatus.BAD_REQUEST,Bad Request,Validation failed or invalid input.
     * 401,HttpStatus.UNAUTHORIZED,Unauthorized,User is not logged in.
     * 403,HttpStatus.FORBIDDEN,Forbidden,User is logged in but lacks permission.
     * 404,HttpStatus.NOT_FOUND,Not Found,Resource doesn't exist.
     * 500,HttpStatus.INTERNAL_SERVER_ERROR,Server Error,Uncaught exception/crash.
     */

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<APIError<Map<String, List<String>>>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex) {

        // Grouping fields by name and collecting messages into a list
        Map<String, List<String>> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())));

        return ResponseEntity.badRequest().body(createApiError(errors));
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<APIError<String>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        return ResponseEntity.badRequest().body(createApiError(ex.getMostSpecificCause().getMessage()));
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<APIError<String>> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(createApiError(ex.getMessage()));
    }

    @ExceptionHandler(value = EntityExistsException.class)
    public ResponseEntity<APIError<String>> handleEntityExistsException(EntityExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(createApiError(ex.getMessage()));
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<APIError<String>> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createApiError(ex.getMessage()));
    }

    @ExceptionHandler(value = NotAcceptableStatusException.class)
    public ResponseEntity<APIError<String>> handleNotAcceptableStatus(NotAcceptableStatusException err) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(createApiError(err.getReason()));
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<APIError<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createApiError(ex.getMessage()));
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<APIError<String>> handleHttpMessageNotReadable(HttpMessageNotReadableException err) {
        Throwable cause = err.getMostSpecificCause();
        if (cause instanceof DateTimeParseException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    createApiError(err.getMostSpecificCause().getMessage() + ". Use format: 'yyyy-MM-ddTHH:mm:ss'"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createApiError(err.getMessage()));
    }

    @ExceptionHandler(value = HttpServerErrorException.InternalServerError.class)
    public ResponseEntity<APIError<String>> handleHttpServerErrorException(HttpServerErrorException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createApiError(ex.getMessage()));
    }

    private <T> APIError<T> createApiError(T errors) {
        return new APIError<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                errors);
    }
}
