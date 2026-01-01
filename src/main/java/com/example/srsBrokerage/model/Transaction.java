package com.example.srsBrokerage.model;

import com.example.srsBrokerage.enums.TransactionType;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(name = "amount", nullable = false, precision = 19, scale = 4)
    @ColumnDefault("0.0000")
    private BigDecimal transactionAmount;

    @Column(name = "currency", nullable = false, length = 3)
    private String transactionCurrency;

    @Column(name = "description")
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
            TransactionType transactionType,
            BigDecimal transactionAmount,
            String transactionCurrency,
            String transactionDescription,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
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
    public TransactionType getTransactionType() {
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
    public void setTransactionType(TransactionType transactionType) {
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
                "Transaction ID =" + id +
                ", User Account =" + account +
                ", Transaction Type ='" + transactionType + '\'' +
                ", Transaction Amount =" + transactionAmount +
                ", Transaction Currency ='" + transactionCurrency + '\'' +
                ", Transaction Description ='" + transactionDescription + '\'' +
                ", Created At =" + createdAt +
                ", Updated At =" + updatedAt +
                '}';
    }
}
