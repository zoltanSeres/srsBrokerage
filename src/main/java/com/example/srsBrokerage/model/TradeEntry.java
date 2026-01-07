package com.example.srsBrokerage.model;

import com.example.srsBrokerage.enums.LedgerDirection;
import com.example.srsBrokerage.enums.TradeEntryType;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "trade_entry", schema = "trades")
public class TradeEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "trade_id")
    private Trade trade;

    @Column(name = "asset_id")
    private Long assetId;

    @Column(name = "entry_type", nullable = false)
    private TradeEntryType tradeEntryType;

    @Column(name = "amount", nullable = false, precision = 19, scale = 6)
    @ColumnDefault("0.0000")
    @PositiveOrZero
    private BigDecimal amount;

    @Column(name = "direction", nullable = false)
    private LedgerDirection ledgerDirection;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


    public TradeEntry() {}
    public TradeEntry(
            Long id,
            Long accountId,
            Trade trade,
            Long assetId,
            TradeEntryType tradeEntryType,
            BigDecimal amount,
            LedgerDirection ledgerDirection,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.accountId = accountId;
        this.trade = trade;
        this.assetId = assetId;
        this.tradeEntryType = tradeEntryType;
        this.amount = amount;
        this.ledgerDirection = ledgerDirection;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Trade getTrade() {
        return trade;
    }

    public Long getAssetId() {
        return assetId;
    }

    public TradeEntryType getTradeEntryType() {
        return tradeEntryType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LedgerDirection getLedgerDirection() {
        return ledgerDirection;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setTrade(Trade trade) {
        this.trade = trade;
    }

    public  void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public void setTradeEntryType(TradeEntryType tradeEntryType) {
        this.tradeEntryType = tradeEntryType;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setLedgerDirection(LedgerDirection ledgerDirection) {
        this.ledgerDirection = ledgerDirection;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    @Override
    public String toString() {
        return "TradeEntry{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", trade=" + trade +
                ", assetId=" + assetId +
                ", tradeEntryType=" + tradeEntryType +
                ", amount=" + amount +
                ", ledgerDirection=" + ledgerDirection +
                ", createdAt=" + createdAt +
                '}';
    }
}
