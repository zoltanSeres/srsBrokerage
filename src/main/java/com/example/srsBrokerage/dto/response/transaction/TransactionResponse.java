package com.example.srsBrokerage.dto.response.transaction;

import com.example.srsBrokerage.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
        Long transactionId,
        TransactionType transactionType,
        Long accountId,
        BigDecimal transactionAmount,
        String transactionDescription,
        LocalDateTime createdAt
) {
}
