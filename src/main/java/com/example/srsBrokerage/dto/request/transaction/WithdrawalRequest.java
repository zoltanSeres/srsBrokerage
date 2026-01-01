package com.example.srsBrokerage.dto.request.transaction;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record WithdrawalRequest(
        @NotNull
        Long accountId,
        @NotNull
        @Positive
        BigDecimal transactionAmount,
        String transactionDescription
) {
}
