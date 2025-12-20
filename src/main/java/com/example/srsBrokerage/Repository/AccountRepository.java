package com.example.srsBrokerage.Repository;

import com.example.srsBrokerage.Model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
