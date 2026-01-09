package com.example.srsBrokerage.external;

import com.example.srsBrokerage.dto.response.asset.ExternalAssetResponse;

public interface MarketDataClient {
    ExternalAssetResponse getAssetData(String assetSymbol);
}
