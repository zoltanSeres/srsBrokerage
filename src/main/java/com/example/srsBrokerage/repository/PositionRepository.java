package com.example.srsBrokerage.repository;

import com.example.srsBrokerage.model.Account;
import com.example.srsBrokerage.model.Asset;
import com.example.srsBrokerage.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {
    Optional<Position> findByAccountAndAsset(Long accountId, Long assetId);

    List<Position> findByAccountId(Long accountId);
}
