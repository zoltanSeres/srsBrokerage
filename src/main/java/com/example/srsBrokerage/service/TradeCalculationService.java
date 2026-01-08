package com.example.srsBrokerage.service;

import java.math.BigDecimal;

import java.math.RoundingMode;

public final class TradeCalculationService {
    public static BigDecimal initialAveragePrice(
            BigDecimal tradePrice
    ) {
        return tradePrice;
    }

    public static BigDecimal weightedAveragePrice(
            BigDecimal currentAveragePrice,
            BigDecimal currentQuantity,
            BigDecimal tradePrice,
            BigDecimal tradeQuantity
    ) {
        BigDecimal currentValue = currentAveragePrice.multiply(currentQuantity);
        BigDecimal tradeValue = tradePrice.multiply(tradeQuantity);
        BigDecimal totalQuantity = currentQuantity.add(tradeQuantity);

        return currentValue
                .add(tradeValue)
                .divide(totalQuantity, 8, RoundingMode.HALF_UP);
    }
}
