package com.example.srsBrokerage.exceptions;

public class InsufficientHeldAssetsException extends RuntimeException{
    public InsufficientHeldAssetsException(String message) {
        super(message);
    }
}
