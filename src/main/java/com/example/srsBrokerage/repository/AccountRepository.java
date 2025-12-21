package com.example.srsBrokerage.repository;

import com.example.srsBrokerage.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
