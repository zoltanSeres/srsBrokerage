package com.example.srsBrokerage.exceptions;

public class InvalidWithdrawalAmountException extends RuntimeException {
    public InvalidWithdrawalAmountException(String message) {
        super(message);
    }
}
