package com.example.srsBrokerage.repository;

import com.example.srsBrokerage.enums.AccountType;
import com.example.srsBrokerage.model.Account;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByUserIdAndAccountType(@NotNull Long aLong, @NotBlank AccountType accountType);
}
