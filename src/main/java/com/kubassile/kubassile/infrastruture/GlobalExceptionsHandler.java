package com.kubassile.kubassile.infrastruture;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kubassile.kubassile.exceptions.DataIntegrityViolationException;
import com.kubassile.kubassile.exceptions.NotFoundException;
import com.kubassile.kubassile.exceptions.ForbiddenException;

@RestControllerAdvice
public class GlobalExceptionsHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFound(NotFoundException ex) {
        return ApiExceptionResponse.generateResponseException(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrity(DataIntegrityViolationException ex) {
        return ApiExceptionResponse.generateResponseException(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Object> handleForbidden(ForbiddenException ex) {
        return ApiExceptionResponse.generateResponseException(HttpStatus.FORBIDDEN, ex.getMessage());
    }
}
