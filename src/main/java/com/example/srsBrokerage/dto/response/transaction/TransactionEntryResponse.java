package com.example.srsBrokerage.dto.response.transaction;

import com.example.srsBrokerage.enums.MoneyCurrency;
import com.example.srsBrokerage.enums.LedgerDirection;

import java.math.BigDecimal;

public record TransactionEntryResponse(
        Long accountId,
        BigDecimal transactionAmount,
        MoneyCurrency currency,
        LedgerDirection ledgerDirection
) {
}
