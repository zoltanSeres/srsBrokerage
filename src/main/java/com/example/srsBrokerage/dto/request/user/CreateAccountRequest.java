package com.example.srsBrokerage.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CreateAccountRequest(

        @NotBlank
        String accountType,

        @NotBlank
        @NotNull
        @Positive
        BigDecimal accountBalance,
        @NotBlank
        String accountCurrency

) {}
