package com.example.srsBrokerage.Model;

import jakarta.persistence.*;

import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "trade", schema = "trades")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "asset_id", nullable = false)
    private Long assetId;

    @Column(name = "quantity_traded", nullable = false, precision = 19, scale = 6)
    @ColumnDefault("0.0000")
    @PositiveOrZero
    private BigDecimal quantityTraded;

    @Column(name = "price", nullable = false, precision = 19, scale = 6)
    @ColumnDefault("0.0000")
    @PositiveOrZero
    private BigDecimal tradePrice;

    @Column(name = "side", nullable = false, length = 10)
    private String tradeSide;

    @Column(name = "trading_fee", nullable = false, precision = 19, scale = 4)
    @ColumnDefault("0.0000")
    @PositiveOrZero
    private BigDecimal tradingFee;

    @Column(name = "executed_at")
    private LocalDateTime executedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    public Trade() {}
    public Trade(
            Long id,
            Long accountId,
            Long assetId,
            BigDecimal quantityTraded,
            BigDecimal tradePrice,
            String tradeSide,
            BigDecimal tradingFee,
            LocalDateTime executedAt,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.accountId = accountId;
        this.assetId = assetId;
        this.quantityTraded = quantityTraded;
        this.tradePrice = tradePrice;
        this.tradeSide = tradeSide;
        this.tradingFee = tradingFee;
        this.executedAt = executedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public Long getId() {
        return id;
    }
    public Long getAccountId() {
        return accountId;
    }
    public Long getAssetId() {
        return assetId;
    }
    public BigDecimal getQuantityTraded() {
        return quantityTraded;
    }
    public BigDecimal getTradePrice() {
        return tradePrice;
    }
    public String getTradeSide() {
        return tradeSide;
    }
    public BigDecimal getTradingFee() {
        return tradingFee;
    }
    public LocalDateTime getExecutedAt() {
        return executedAt;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }


    public void setId(Long id) {
        this.id = id;
    }
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }
     public void setQuantityTraded(BigDecimal quantityTraded) {
        this.quantityTraded = quantityTraded;
     }
     public void setTradePrice(BigDecimal tradePrice) {
        this.tradePrice = tradePrice;
     }
     public void setTradeSide(String tradeSide) {
        this.tradeSide = tradeSide;
     }
     public void setTradingFee(BigDecimal tradingFee) {
        this.tradingFee = tradingFee;
     }
     public void setExecutedAt(LocalDateTime executedAt) {
        this.executedAt = executedAt;
     }
     public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
     }
     public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
     }


    @Override
    public String toString() {
        return "Trade{" +
                "Trade ID =" + id +
                ", Account ID =" + accountId +
                ", Asset ID =" + assetId +
                ", Quantity Traded =" + quantityTraded +
                ", Trade Price =" + tradePrice +
                ", Trade Side ='" + tradeSide + '\'' +
                ", Trading Fee =" + tradingFee +
                ", Executed At =" + executedAt +
                ", Created At =" + createdAt +
                ", Updated At =" + updatedAt +
                '}';
    }
}
