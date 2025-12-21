package com.example.srsBrokerage.model;

import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "asset", schema = "assets")
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "symbol", nullable = false, length = 20)
    private String assetSymbol;

    @Column(name = "name", nullable = false)
    private String assetName;

    @Column(name = "currency", nullable = false, length = 3)
    private String assetCurrency;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "update_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    public Asset() {}
    public Asset(
            Long id,
            String assetSymbol,
            String assetName,
            String assetCurrency,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.assetSymbol = assetSymbol;
        this.assetName = assetName;
        this.assetCurrency = assetCurrency;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public Long getId() {
        return id;
    }
    public String getAssetSymbol() {
        return assetSymbol;
    }
    public String getAssetName() {
        return assetName;
    }
    public String getAssetCurrency() {
        return assetCurrency;
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
    public void setAssetSymbol(String assetSymbol) {
        this.assetSymbol = assetSymbol;
    }
    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }
    public void setAssetCurrency(String assetCurrency) {
        this.assetCurrency = assetCurrency;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


    @Override
    public String toString() {
        return "Asset{" +
                "Asset ID =" + id +
                ", Asset Symbol ='" + assetSymbol + '\'' +
                ", Asset Name ='" + assetName + '\'' +
                ", Asset Currency ='" + assetCurrency + '\'' +
                ", Created At =" + createdAt +
                ", Updated At =" + updatedAt +
                '}';
    }
}
