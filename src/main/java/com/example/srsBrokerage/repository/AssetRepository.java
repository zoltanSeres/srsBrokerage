package com.example.srsBrokerage.repository;

import com.example.srsBrokerage.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Long> {
}
