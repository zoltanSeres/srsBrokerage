package com.example.srsBrokerage.dto.request.transaction;

import java.math.BigDecimal;

public record DepositRequest(
        Long accountId,
        BigDecimal transactionAmount,
        String transactionDescription
) {
}
