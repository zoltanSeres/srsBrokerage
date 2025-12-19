package com.example.srsBrokerage.Model;

import jakarta.persistence.*;

import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "position", schema = "accounts")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;

    @Column(name = "quantity_held", nullable = false, precision = 19, scale = 6)
    @ColumnDefault("0.0000")
    @PositiveOrZero
    private BigDecimal heldQuantity;

    @Column(name = "average_price", nullable = false, precision = 19, scale = 4)
    @ColumnDefault("0.0000")
    @PositiveOrZero
    private BigDecimal averagePrice;

    @Column(name = "create_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    public Position() {}
    public Position(
            Long id,
            Account account,
            Asset asset,
            BigDecimal heldQuantity,
            BigDecimal averagePrice,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.account = account;
        this.asset = asset;
        this.heldQuantity = heldQuantity;
        this.averagePrice = averagePrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public Long getId() {
        return id;
    }
    public Account getAccount() {
        return account;
    }
    public Asset getAsset() {
        return asset;
    }
    public BigDecimal getHeldQuantity() {
        return heldQuantity;
    }
    public BigDecimal getAveragePrice() {
        return averagePrice;
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
    public void setAccount(Account account) {
        this.account = account;
    }
    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public void setHeldQuantity(BigDecimal heldQuantity) {
        this.heldQuantity = heldQuantity;
    }
    public void setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", account=" + account +
                ", asset=" + asset +
                ", heldQuantity=" + heldQuantity +
                ", averagePrice=" + averagePrice +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
