package com.example.srsBrokerage.dto.request.asset;

import jakarta.validation.constraints.NotNull;

import java.util.Currency;

public record AssetRequest(
        @NotNull
        String assetSymbol,
        @NotNull
        String assetName,
        @NotNull
        Currency currency
) {
}
