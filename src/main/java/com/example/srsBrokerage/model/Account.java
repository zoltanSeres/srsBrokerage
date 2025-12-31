package com.example.srsBrokerage.model;

import com.example.srsBrokerage.Enum.AccountType;
import jakarta.persistence.*;

import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;

@Entity
@Table(name = "account", schema = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "account_type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(name = "cash_balance", nullable = false, precision = 19, scale = 4)
    @ColumnDefault("0.0000")
    @PositiveOrZero
    private BigDecimal accountBalance;

    @Column(name = "currency", nullable = false, length = 3)
    @Enumerated(EnumType.STRING)
    private Currency accountCurrency;

    @OneToMany(mappedBy = "account")            // maybe add Cascade type PERSIST and ORDER BY DATE
    private List<Transaction> transactions;

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        transaction.setAccount(this);
    }

    @OneToMany(mappedBy = "account")            // maybe add Cascade type PERSIST and ORDER BY DATE
    private List<Position> positions;

    public void addPosition(Position position) {
        positions.add(position);
        position.setAccount(this);
    }
    public void removePosition(Position position){
        positions.remove(position);
        position.setAccount(null);
    }

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
            AccountType accountType,
            BigDecimal accountBalance,
            Currency accountCurrency,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.userId = userId;
        this.accountType = accountType;
        this.accountBalance = accountBalance;
        this.accountCurrency = accountCurrency;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }
    public Long getUserId() {
        return userId;
    }
    public AccountType getAccountType() {
        return accountType;
    }
    public BigDecimal getAccountBalance() {
        return accountBalance;
    }
    public Currency getAccountCurrency() {
        return accountCurrency;
    }
    public List<Transaction> getTransactions() {
        return transactions;
    }
    public List<Position> getPositions() {
        return positions;
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
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
    public void setAccountBalance(BigDecimal cashBalance) {
        this.accountBalance = accountBalance;
    }
    public void setCurrency(Currency accountCurrency) {
        this.accountCurrency = accountCurrency;
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
                "Account ID =" + id +
                ", User ID =" + userId +
                ", Account Type ='" + accountType + '\'' +
                ", Account Balance =" + accountBalance +
                ", Account Currency ='" + accountCurrency + '\'' +
                ", Transactions =" + transactions +
                ", Positions =" + positions +
                ", Created At =" + createdAt +
                ", Updated At =" + updatedAt +
                '}';
    }
}
