package com.example.srsBrokerage.exceptions;

public class AccountCannotBeClosedException extends RuntimeException{
    public AccountCannotBeClosedException(String message) {
        super(message);
    }
}
