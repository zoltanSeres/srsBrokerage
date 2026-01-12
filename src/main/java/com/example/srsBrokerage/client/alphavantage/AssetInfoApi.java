package com.example.srsBrokerage.client.alphavantage;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AssetInfoApi(
        @JsonProperty("01. symbol")
        String assetSymbol,
        @JsonProperty("05. price")
        String assetPrice
) {
}

// represents the actual quote data from the json
