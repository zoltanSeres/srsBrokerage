package com.example.srsBrokerage.external;

import com.example.srsBrokerage.dto.response.asset.ExternalAssetResponse;

import java.math.BigDecimal;

public class DummyMarketDataClient implements MarketDataClient {
    @Override
    public ExternalAssetResponse getAssetData(String assetSymbol) {
        return new ExternalAssetResponse(BigDecimal.ZERO, BigDecimal.ZERO);
    }
}
