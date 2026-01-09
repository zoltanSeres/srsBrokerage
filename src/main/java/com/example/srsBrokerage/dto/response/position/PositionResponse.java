package com.example.srsBrokerage.dto.response.position;

import com.example.srsBrokerage.model.Account;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record PositionResponse(
        Long id,
        Long accountId,
        Long assetId,
        BigDecimal heldQuantity,
        BigDecimal averagePrice,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
