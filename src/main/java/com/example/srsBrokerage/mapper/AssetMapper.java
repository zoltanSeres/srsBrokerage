package com.example.srsBrokerage.mapper;

import com.example.srsBrokerage.dto.request.asset.AssetRequest;
import com.example.srsBrokerage.dto.response.asset.AssetResponse;
import com.example.srsBrokerage.dto.response.asset.ExternalAssetResponse;
import com.example.srsBrokerage.model.Asset;
import org.springframework.stereotype.Component;
@Component
public class AssetMapper {

    public Asset toEntity(AssetRequest assetRequest) {

        Asset asset = new Asset();

        asset.setAssetSymbol(assetRequest.assetSymbol());
        asset.setAssetName(assetRequest.assetName());
        asset.setAssetCurrency(assetRequest.currency());

        return asset;
    }

    public AssetResponse toDto(Asset asset, ExternalAssetResponse assetResponse) {

        return new AssetResponse(
                asset.getAssetName(),
                asset.getAssetSymbol(),
                assetResponse.assetPrice(),
                asset.getCurrency()
        );
    }

    // in the future do public List<AssetResponse> toDtoList
}
