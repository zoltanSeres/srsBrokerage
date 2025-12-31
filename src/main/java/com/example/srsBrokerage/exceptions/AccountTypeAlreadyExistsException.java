package com.example.srsBrokerage.exceptions;

public class AccountTypeAlreadyExistsException extends RuntimeException{
    public AccountTypeAlreadyExistsException(String message) {
        super(message);
    }
}
