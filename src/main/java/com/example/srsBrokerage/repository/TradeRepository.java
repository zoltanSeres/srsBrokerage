package com.example.srsBrokerage.repository;

import com.example.srsBrokerage.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeRepository extends JpaRepository<Trade, Long> {
    List<Trade> findAllByTradeEntriesAccountId(Long accountId);
}
