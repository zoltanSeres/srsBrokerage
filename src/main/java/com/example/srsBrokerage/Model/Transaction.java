package com.example.srsBrokerage.Model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction", schema = "accounts")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "type", nullable = false, length = 50)
    private String transactionType;

    @Column(name = "amount", nullable = false, precision = 19, scale = 4)
    @ColumnDefault("0.0000")
    private BigDecimal transactionAmount;

    @Column(name = "currency", nullable = false, length = 3)
    private String transactionCurrency;

    @Column(name = "description", length = 254)
    private String transactionDescription;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    public Transaction() {}
    public Transaction(
            Long id,
            Account account,
            String transactionType,
            BigDecimal transactionAmount,
            String transactionCurrency,
            String transactionDescription,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.account = account;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.transactionCurrency = transactionCurrency;
        this.transactionDescription = transactionDescription;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public Long getId() {
        return id;
    }
    public Account getAccount() {
        return account;
    }
    public String getTransactionType() {
        return transactionType;
    }
    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }
    public String getTransactionCurrency() {
        return transactionCurrency;
    }
    public String getTransactionDescription() {
        return transactionDescription;
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
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
    public void setTransactionCurrency(String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }
    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", account=" + account +
                ", transactionType='" + transactionType + '\'' +
                ", transactionAmount=" + transactionAmount +
                ", transactionCurrency='" + transactionCurrency + '\'' +
                ", transactionDescription='" + transactionDescription + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
