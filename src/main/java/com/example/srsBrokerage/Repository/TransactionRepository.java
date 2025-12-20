package com.example.srsBrokerage.Repository;

import com.example.srsBrokerage.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
