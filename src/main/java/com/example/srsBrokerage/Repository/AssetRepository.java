package com.example.srsBrokerage.Repository;

import com.example.srsBrokerage.Model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Long> {
}
