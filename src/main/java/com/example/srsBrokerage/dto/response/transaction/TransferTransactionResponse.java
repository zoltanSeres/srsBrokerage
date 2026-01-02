package com.example.srsBrokerage.dto.response.transaction;

import com.example.srsBrokerage.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferTransactionResponse(
        Long transactionId,
        TransactionType transactionType,
        Long fromAccountId,
        Long toAccountId,
        BigDecimal transactionAmount,
        String transactionDescription,
        LocalDateTime createdAt
) {
}
