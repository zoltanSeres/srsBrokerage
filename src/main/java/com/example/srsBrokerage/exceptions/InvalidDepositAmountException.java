package com.example.srsBrokerage.exceptions;

public class InvalidDepositAmountException extends RuntimeException{
    public InvalidDepositAmountException(String message) {
        super(message);
    }
}
