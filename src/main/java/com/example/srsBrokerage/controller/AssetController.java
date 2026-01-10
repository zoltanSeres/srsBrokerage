package com.example.srsBrokerage.controller;

import com.example.srsBrokerage.dto.response.asset.AssetResponse;
import com.example.srsBrokerage.service.AssetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/assets")
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping("/{assetSymbol}")
    public ResponseEntity<AssetResponse> getAssetBySymbol(@PathVariable String assetSymbol) {
        AssetResponse assetResponse = assetService.getAssetBySymbol(assetSymbol);
        return ResponseEntity.ok(assetResponse);
    }
}
