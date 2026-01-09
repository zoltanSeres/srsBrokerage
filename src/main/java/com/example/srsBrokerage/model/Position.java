package com.example.srsBrokerage.model;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "asset_id", nullable = false)
    private Long assetId;

    @Column(name = "quantity_held", nullable = false, precision = 19, scale = 6)
    @ColumnDefault("0.0000")
    @PositiveOrZero
    private BigDecimal heldQuantity;

    @Column(name = "average_price", nullable = false, precision = 19, scale = 8)
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
            Long assetId,
            BigDecimal heldQuantity,
            BigDecimal averagePrice,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.assetId = assetId;
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
    public Long getAssetId() {
        return assetId;
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
    public void setAssetId(Long assetId) {
        this.assetId = assetId;
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
                "Position ID =" + id +
                ", Account =" + account +
                ", Asset =" + assetId +
                ", Held Quantity =" + heldQuantity +
                ", Average Price =" + averagePrice +
                ", Created At =" + createdAt +
                ", Updated At =" + updatedAt +
                '}';
    }
}
