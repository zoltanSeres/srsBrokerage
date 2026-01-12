package com.example.srsBrokerage.mapper;

import com.example.srsBrokerage.dto.api.ExternalApiResponse;
import com.example.srsBrokerage.dto.api.AssetInfoApi;
import com.example.srsBrokerage.dto.response.asset.ExternalAssetResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AssetInfoMapper {

    public ExternalAssetResponse toExternal(ExternalApiResponse externalApiResponse) {

        AssetInfoApi assetInfoApi = externalApiResponse.assetInfoApi();

        return new ExternalAssetResponse(
                assetInfoApi.assetSymbol(),
                new BigDecimal(assetInfoApi.assetPrice())
        );
    }
}
