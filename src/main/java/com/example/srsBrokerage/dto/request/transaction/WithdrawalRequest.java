package com.example.srsBrokerage.dto.request.transaction;

import com.example.srsBrokerage.enums.AccountCurrency;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record WithdrawalRequest(
        @NotNull
        Long accountId,
        @NotNull
        @Positive
        BigDecimal transactionAmount,
        @NotNull
        AccountCurrency currency,
        String transactionDescription
) {
}
