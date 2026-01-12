package com.example.srsBrokerage.dto.request.trade;

import com.example.srsBrokerage.enums.TradeSide;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TradeRequest(
        @NotNull
        Long accountId,
        @NotBlank
        String assetSymbol,
        @NotNull
        BigDecimal quantityTraded,
        @NotNull
        TradeSide tradeSide
) {
}
