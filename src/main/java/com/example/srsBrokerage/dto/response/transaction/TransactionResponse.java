package com.example.srsBrokerage.dto.response.transaction;

import com.example.srsBrokerage.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record TransactionResponse(
        Long transactionId,
        TransactionType transactionType,
        String transactionDescription,
        LocalDateTime createdAt,
        List<TransactionEntryResponse> transactionEntryResponse
) {
}
