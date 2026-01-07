package com.example.srsBrokerage.repository;

import com.example.srsBrokerage.model.TradeEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeEntryRepository extends JpaRepository<TradeEntry, Long> {
}
