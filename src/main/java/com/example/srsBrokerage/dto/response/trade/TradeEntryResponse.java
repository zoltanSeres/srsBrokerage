package com.example.srsBrokerage.dto.response.trade;

import com.example.srsBrokerage.enums.LedgerDirection;
import com.example.srsBrokerage.enums.TradeEntryType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TradeEntryResponse(
        Long id,
        Long accountId,
        Long assetId,
        TradeEntryType tradeEntryType,
        BigDecimal amount,
        LedgerDirection ledgerDirection,
        LocalDateTime createdAt
) {
}
