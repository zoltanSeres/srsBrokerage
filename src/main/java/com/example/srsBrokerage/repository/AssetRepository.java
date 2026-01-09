package com.example.srsBrokerage.repository;

import com.example.srsBrokerage.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    Optional<Asset> findByAssetSymbol(String assetSymbol);
    }
