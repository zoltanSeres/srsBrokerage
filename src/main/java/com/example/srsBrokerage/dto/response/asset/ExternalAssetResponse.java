package com.example.srsBrokerage.dto.response.asset;

import java.math.BigDecimal;

public record ExternalAssetResponse(
        String assetSymbol,
        BigDecimal assetPrice
) {
}
// converts the api string to types to use it in app