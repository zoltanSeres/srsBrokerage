package com.example.srsBrokerage.dto.response.account;

import com.example.srsBrokerage.enums.MoneyCurrency;
import com.example.srsBrokerage.enums.AccountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponse(

        Long id,
        Long userId,
        AccountType accountType,
        BigDecimal accountBalance,
        MoneyCurrency accountCurrency,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {}
