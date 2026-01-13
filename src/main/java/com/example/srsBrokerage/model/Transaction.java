package com.example.srsBrokerage.model;

import com.example.srsBrokerage.enums.TransactionType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "transaction", schema = "accounts")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(name = "description")
    private String transactionDescription;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
    private List<TransactionEntry> transactionEntries = new ArrayList<>();

    public void addTransactionEntry(TransactionEntry entry) {
        transactionEntries.add(entry);
        entry.setTransaction(this);
    }


    public Transaction() {}
    public Transaction(
            Long id,
            TransactionType transactionType,
            String transactionDescription,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.transactionType = transactionType;
        this.transactionDescription = transactionDescription;
        this.createdAt = createdAt;
    }


    public Long getId() {
        return id;
    }
    public TransactionType getTransactionType() {
        return transactionType;
    }
    public String getTransactionDescription() {
        return transactionDescription;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public List<TransactionEntry> getTransactionEntries() {
        return transactionEntries;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }
    public void setTransactionEntries(List<TransactionEntry> transactionEntries) {
        this.transactionEntries = transactionEntries;
    }


    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", transactionType=" + transactionType +
                ", transactionDescription='" + transactionDescription + '\'' +
                ", createdAt=" + createdAt +
                ", transactionEntries=" + transactionEntries +
                '}';
    }
}
