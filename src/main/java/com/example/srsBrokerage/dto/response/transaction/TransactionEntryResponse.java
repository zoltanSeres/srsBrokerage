package com.example.srsBrokerage.dto.response.transaction;

import com.example.srsBrokerage.enums.Currency;
import com.example.srsBrokerage.enums.EntryType;

import java.math.BigDecimal;

public record TransactionEntryResponse(
        Long accountId,
        BigDecimal transactionAmount,
        Currency currency,
        EntryType entryType
) {
}
