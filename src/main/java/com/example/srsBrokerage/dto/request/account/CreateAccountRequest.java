package com.example.srsBrokerage.dto.request.account;

import com.example.srsBrokerage.enums.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.Currency;

public record CreateAccountRequest(

        @NotNull
        Long userId,

        @NotBlank
        AccountType accountType,

        @NotNull
        @Positive
        BigDecimal accountBalance,
        @NotNull
        Currency accountCurrency

) {}
