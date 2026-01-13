package com.example.srsBrokerage.model;

import com.example.srsBrokerage.enums.TradeSide;
import jakarta.persistence.*;

import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trade", schema = "trades")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "side", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private TradeSide tradeSide;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "trade", cascade = CascadeType.ALL)
    private List<TradeEntry> tradeEntries = new ArrayList<>();


    public Trade() {}
    public Trade(
            Long id,
            TradeSide tradeSide,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.tradeSide = tradeSide;
        this.createdAt = createdAt;
    }


    public Long getId() {
        return id;
    }
    public TradeSide getTradeSide() {
        return tradeSide;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public List<TradeEntry> getTradeEntries() {
        return tradeEntries;
    }

    public void setTradeId(Long id) {
        this.id = id;
    }
     public void setTradeSide(TradeSide tradeSide) {
        this.tradeSide = tradeSide;
     }
     public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
     }
     public void  setTradeEntries(List<TradeEntry> tradeEntries) {
        this.tradeEntries = tradeEntries;
     }


    @Override
    public String toString() {
        return "Trade{" +
                "id=" + id +
                ", tradeSide=" + tradeSide +
                ", createdAt=" + createdAt +
                ", tradeEntries=" + tradeEntries +
                '}';
    }
}
