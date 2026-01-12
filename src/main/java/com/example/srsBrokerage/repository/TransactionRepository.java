package com.example.srsBrokerage.repository;

import com.example.srsBrokerage.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findDistinctByTransactionEntries_Account_Id(Long accountId);
}
