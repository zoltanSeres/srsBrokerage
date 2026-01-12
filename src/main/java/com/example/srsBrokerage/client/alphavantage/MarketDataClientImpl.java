package com.example.srsBrokerage.client.alphavantage;

import com.example.srsBrokerage.client.MarketDataClient;
import com.example.srsBrokerage.config.WebClientConfig;
import com.example.srsBrokerage.dto.request.asset.AssetRequest;
import com.example.srsBrokerage.dto.response.asset.ExternalAssetResponse;
import com.example.srsBrokerage.exceptions.AssetDataFetchException;
import com.example.srsBrokerage.exceptions.AssetDataNotFoundException;
import com.example.srsBrokerage.exceptions.ExternalApiException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.math.BigDecimal;

@Component
public class MarketDataClientImpl implements MarketDataClient {

    private final WebClient webClient;
    private final AssetInfoMapper assetInfoMapper;

    public MarketDataClientImpl(
            WebClient webClient,
            AssetInfoMapper assetInfoMapper
    ) {
        this.webClient = webClient;
        this.assetInfoMapper = assetInfoMapper;
    }

    @Override
    @Cacheable(value = "assetPriceCache", key = "#assetSymbol", unless = "#result == null")
    public ExternalAssetResponse getAssetData(String assetSymbol) {
        try {
            ExternalApiResponse externalApiResponse = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("function", "GLOBAL_QUOTE")
                            .queryParam("symbol", assetSymbol)
                            .queryParam("apikey", "{apikey}")
                            .build())
                    .retrieve()
                    .bodyToMono(ExternalApiResponse.class)
                    .block();

            if (externalApiResponse == null || externalApiResponse.assetInfoApi() == null) {
                throw new AssetDataNotFoundException("No data found for " + assetSymbol + " .");
            }

            return assetInfoMapper.toExternal(externalApiResponse);
        } catch (WebClientResponseException exception) {
            throw new ExternalApiException("Alpha Vantage error: " + exception.getMessage());
        } catch (Exception exception) {
            throw new AssetDataFetchException("Failed to fetch data for " + assetSymbol);
        }
    }
}

