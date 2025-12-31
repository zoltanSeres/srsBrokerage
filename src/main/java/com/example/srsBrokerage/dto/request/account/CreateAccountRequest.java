package com.example.srsBrokerage.dto.request.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.Currency;

public record CreateAccountRequest(

        @NotBlank
        String accountType,

        @NotNull
        @Positive
        BigDecimal accountBalance,
        @NotNull
        Currency accountCurrency

) {}
