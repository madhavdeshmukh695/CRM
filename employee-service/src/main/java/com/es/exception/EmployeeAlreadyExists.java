package com.es.exception;

public class EmployeeAlreadyExists extends RuntimeException {
    public EmployeeAlreadyExists(String msg) {
        super(msg);
    }
}
