package com.example.srsBrokerage.dto.response;

public record ErrorResponse(
        int status,
        String message
) {}
