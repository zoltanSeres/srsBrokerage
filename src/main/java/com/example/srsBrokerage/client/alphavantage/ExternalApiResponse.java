package com.example.srsBrokerage.client.alphavantage;

import com.example.srsBrokerage.client.alphavantage.AssetInfoApi;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ExternalApiResponse(
        @JsonProperty("Global Quote")
        AssetInfoApi assetInfoApi
) {
}
// represents the entire json response from Alpha Vantage