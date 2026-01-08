package com.example.srsBrokerage.dto.response.asset;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

public record AssetResponse(
        String assetSymbol,
        String assetName,
        Currency currency,
        BigDecimal assetPrice, // API
        BigDecimal change24h, // API
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
