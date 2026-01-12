package com.example.srsBrokerage.repository;

import com.example.srsBrokerage.model.TradeEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TradeEntryRepository extends JpaRepository<TradeEntry, Long> {
    @Query("SELECT t FROM TradeEntry t WHERE t.accountId = :accountId")
    List<TradeEntry> findByAccountId(@Param("accountId") Long accountId);
}
