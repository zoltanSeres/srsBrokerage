package com.example.srsBrokerage.dto.response.account;

import com.example.srsBrokerage.enums.AccountCurrency;
import com.example.srsBrokerage.enums.AccountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponse(

        Long id,
        Long userId,
        BigDecimal accountBalance,
        AccountType accountType,
        AccountCurrency accountCurrency,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {}
