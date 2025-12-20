package com.example.srsBrokerage.Repository;

import com.example.srsBrokerage.Model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<Trade, Long> {
}
