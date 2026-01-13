package com.example.srsBrokerage.model;

import com.example.srsBrokerage.enums.MoneyCurrency;
import com.example.srsBrokerage.enums.LedgerDirection;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_entry", schema = "accounts")
public class TransactionEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "amount", nullable = false, precision = 19, scale = 4)
    @ColumnDefault("0.0000")
    private BigDecimal transactionAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false, length = 3)
    private MoneyCurrency transactionCurrency;

    @Enumerated(EnumType.STRING)
    @Column(name = "entry-type", nullable = false, length = 10)
    private LedgerDirection ledgerDirection;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;


    public TransactionEntry() {}
    public TransactionEntry(
            Long id,
            Transaction transaction,
            Account account,
            BigDecimal transactionAmount,
            MoneyCurrency transactionCurrency,
            LedgerDirection ledgerDirection,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.transaction = transaction;
        this.account = account;
        this.transactionAmount = transactionAmount;
        this.transactionCurrency = transactionCurrency;
        this.ledgerDirection = ledgerDirection;
        this.createdAt = createdAt;
    }


    public Long getId() {
        return id;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public Account getAccount() {
        return account;
    }

    public  BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public MoneyCurrency getTransactionCurrency() {
        return transactionCurrency;
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

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public void setTransactionCurrency(MoneyCurrency transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }

    public void setLedgerDirection(LedgerDirection ledgerDirection) {
        this.ledgerDirection = ledgerDirection;
    }


    @Override
    public String toString() {
        return "TransactionEntry{" +
                "id=" + id +
                ", transaction=" + transaction +
                ", account=" + account +
                ", transactionAmount=" + transactionAmount +
                ", transactionCurrency=" + transactionCurrency +
                ", createdAt=" + createdAt +
                '}';
    }
}
