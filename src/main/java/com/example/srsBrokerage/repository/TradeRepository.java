package com.example.srsBrokerage.repository;

import com.example.srsBrokerage.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<Trade, Long> {
}
