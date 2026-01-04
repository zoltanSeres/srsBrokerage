package com.example.srsBrokerage.repository;

import com.example.srsBrokerage.model.TransactionEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionEntryRepository extends JpaRepository<TransactionEntry, Long> {
}
