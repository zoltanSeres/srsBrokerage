package com.example.srsBrokerage.dto.request.account;

import com.example.srsBrokerage.enums.MoneyCurrency;
import com.example.srsBrokerage.enums.AccountType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateAccountRequest(

        @NotNull
        AccountType accountType,
        @NotNull
        MoneyCurrency accountCurrency

) {}
