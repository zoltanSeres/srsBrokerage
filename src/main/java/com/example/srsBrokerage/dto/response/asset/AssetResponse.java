package com.example.srsBrokerage.dto.response.asset;

import com.example.srsBrokerage.enums.MoneyCurrency;

import java.math.BigDecimal;

public record AssetResponse(
        String assetName,
        String assetSymbol, // API
        BigDecimal assetPrice, // API
        MoneyCurrency assetCurrency
) {
}
