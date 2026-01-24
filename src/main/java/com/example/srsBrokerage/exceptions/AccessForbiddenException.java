package com.example.srsBrokerage.exceptions;

public class AccessForbiddenException extends RuntimeException {
    public AccessForbiddenException(String message) {
        super(message);
    }
}
