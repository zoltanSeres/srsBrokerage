package com.example.srsBrokerage.dto.response.account;

import com.example.srsBrokerage.Enum.AccountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

public record AccountResponse(

        Long id,
        Long userId,
        BigDecimal accountBalance,
        AccountType accountType,
        Currency accountCurrency,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {}
