package com.example.srsBrokerage.dto.response.asset;

import java.math.BigDecimal;

public record ExternalAssetResponse(
        BigDecimal assetPrice,
        BigDecimal change24h
) {
}
