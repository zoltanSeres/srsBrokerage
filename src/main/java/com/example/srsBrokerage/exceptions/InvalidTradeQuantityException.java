package com.example.srsBrokerage.exceptions;

public class InvalidTradeQuantityException extends RuntimeException {
    public InvalidTradeQuantityException(String message) {
        super(message);
    }
}
