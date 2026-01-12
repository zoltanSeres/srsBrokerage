package com.example.srsBrokerage.client.alphavantage;

import com.example.srsBrokerage.client.alphavantage.ExternalApiResponse;
import com.example.srsBrokerage.client.alphavantage.AssetInfoApi;
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
