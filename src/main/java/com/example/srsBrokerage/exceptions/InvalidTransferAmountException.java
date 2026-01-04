package com.example.srsBrokerage.exceptions;

public class InvalidTransferAmountException extends RuntimeException{
    public InvalidTransferAmountException(String message) {
        super(message);
    }
}
