package com.kubassile.kubassile.infrastruture;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionsHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handlerNotFound(NotFoundException ex) {
        return ApiExceptionResponse.generateResponseException(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrity(DataIntegrityViolationException ex) {
        return ApiExceptionResponse.generateResponseException(HttpStatus.CONFLICT, ex.getMessage());
    }
}
