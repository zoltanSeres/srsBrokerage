package com.example.srsBrokerage.dto.response.user;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponse(

        Long id,
        Long userId,
        BigDecimal accountBalance,
        String accountType,
        String accountCurrency,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {}
