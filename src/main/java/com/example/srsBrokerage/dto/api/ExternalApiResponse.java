package com.example.srsBrokerage.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ExternalApiResponse(
        @JsonProperty("Global Quote")
        AssetInfoApi assetInfoApi
) {
}
// represents the entire json response from Alpha Vantage