package com.example.srsBrokerage.dto.request.transaction;

import com.example.srsBrokerage.enums.MoneyCurrency;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record DepositRequest(
        @NotNull
        Long accountId,
        @Positive
        @NotNull
        BigDecimal transactionAmount,
        @NotNull
        MoneyCurrency currency,
        String transactionDescription
) {
}
