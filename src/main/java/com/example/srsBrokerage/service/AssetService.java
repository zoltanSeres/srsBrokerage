package com.example.srsBrokerage.service;

import com.example.srsBrokerage.dto.response.asset.AssetResponse;
import com.example.srsBrokerage.dto.response.asset.ExternalAssetResponse;
import com.example.srsBrokerage.exceptions.AssetNotFoundException;
import com.example.srsBrokerage.external.DummyMarketDataClient;
import com.example.srsBrokerage.external.MarketDataClient;
import com.example.srsBrokerage.mapper.AssetMapper;
import com.example.srsBrokerage.model.Asset;
import com.example.srsBrokerage.repository.AssetRepository;

import java.util.List;
import java.util.Locale;

public class AssetService {
    private final AssetRepository assetRepository;
    private final MarketDataClient marketDataClient;
    private final AssetMapper assetMapper;

    public AssetService(
            AssetRepository assetRepository,
            MarketDataClient marketDataClient,
            AssetMapper assetMapper
    ) {
        this.assetRepository = assetRepository;
        this.marketDataClient = marketDataClient;
        this.assetMapper = assetMapper;
    }


    public List<AssetResponse> getAllAssets() {
        return assetRepository.findAll()
                .stream()
                .map(asset -> {
                   ExternalAssetResponse assetResponse =
                           marketDataClient.getAssetData(asset.getAssetSymbol());
                   return assetMapper.toDto(asset, assetResponse);
                })
                .toList();
    }

    public AssetResponse getAssetBySymbol(String assetSymbol) {
        assetSymbol = formattedAssetSymbol(assetSymbol);

        Asset asset = assetRepository.findByAssetSymbol(assetSymbol)
                .orElseThrow(() -> new AssetNotFoundException("Asset not found."));

        ExternalAssetResponse apiData = marketDataClient.getAssetData(assetSymbol);

        return assetMapper.toDto(asset, apiData);
    }

    private String formattedAssetSymbol(String assetSymbol) {
        return assetSymbol.trim().toUpperCase(Locale.ROOT);
    }
}
