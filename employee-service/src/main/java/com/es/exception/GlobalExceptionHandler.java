package com.es.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<String> handleEmployeeNotFoundException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmployeeAlreadyExists.class)
    public ResponseEntity<String> handleEmployeeAlreadyExistsException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.ALREADY_REPORTED);
    }
}
