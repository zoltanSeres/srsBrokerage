package com.example.srsBrokerage.dto.request.asset;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Currency;

public record AssetRequest(
        @NotBlank
        String assetSymbol,
        @NotBlank
        String assetName,
        @NotNull
        Currency currency
) {
}
