package com.example.srsBrokerage.dto.response.trade;

import com.example.srsBrokerage.enums.TradeSide;
import com.example.srsBrokerage.model.TradeEntry;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record TradeResponse(
        Long tradeId,
        TradeSide tradeSide,
        List<TradeEntryResponse> tradeEntryResponse,
        LocalDateTime createdAt
) {
}
