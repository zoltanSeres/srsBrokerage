package com.example.srsBrokerage.dto.request.transaction;

import com.example.srsBrokerage.enums.MoneyCurrency;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransferRequest(
        @NotNull
        Long fromAccountId,
        @NotNull
        Long toAccountId,
        @NotNull
        @Positive
        BigDecimal transactionAmount,
        @NotNull
        MoneyCurrency currency,
        String transactionDescription
) {
}
