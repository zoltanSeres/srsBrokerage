package com.example.srsBrokerage.dto.request.asset;

import com.example.srsBrokerage.enums.MoneyCurrency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AssetRequest(
        @NotBlank
        String assetSymbol,
        @NotBlank
        String assetName,
        @NotNull
        MoneyCurrency assetCurrency
) {
}
