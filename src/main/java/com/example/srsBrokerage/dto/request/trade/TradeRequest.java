package com.example.srsBrokerage.dto.request.trade;

import com.example.srsBrokerage.enums.TradeSide;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TradeRequest(
        @NotNull
        Long accountId,
        @NotNull
        Long assetId,
        @NotNull
        BigDecimal quantityTraded,
        @NotNull
        TradeSide tradeSide
) {
}
