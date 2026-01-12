package com.example.srsBrokerage.dto.response.asset;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

public record AssetResponse(
        String assetName,
        String assetSymbol, // API
        BigDecimal assetPrice, // API
        Currency currency
) {
}
