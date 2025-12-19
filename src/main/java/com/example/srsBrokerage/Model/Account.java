package com.example.srsBrokerage.Model;

import jakarta.persistence.*;

import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "account", schema = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "account_type", nullable = false, length = 50)
    private String accountType;

    @Column(name = "cash_balance", nullable = false, precision = 19, scale = 4)
    @ColumnDefault("0.0000")
    @PositiveOrZero
    private BigDecimal cashBalance;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    public Account(){}
    public Account(
            Long id,
            Long userId,
            String accountType,
            BigDecimal cashBalance,
            String currency,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.userId = userId;
        this.accountType = accountType;
        this.cashBalance = cashBalance;
        this.currency = currency;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }
    public Long getUserId() {
        return userId;
    }
    public String getAccountType() {
        return accountType;
    }
    public BigDecimal getCashBalance() {
        return cashBalance;
    }
    public String getCurrency() {
        return currency;
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
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    public void setCashBalance(BigDecimal cashBalance) {
        this.cashBalance = cashBalance;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userId=" + userId +
                ", accountType='" + accountType + '\'' +
                ", cashBalance=" + cashBalance +
                ", currency='" + currency + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
