package com.example.srsBrokerage.model;

import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Objects;

@Entity
@Table(name = "asset", schema = "assets")
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "symbol", nullable = false, length = 20, unique = true)
    private String assetSymbol;

    @Column(name = "name", nullable = false)
    private String assetName;

    @Column(name = "currency", nullable = false)
    private Currency currency;


    public Asset() {}
    public Asset(
            Long id,
            String assetSymbol,
            String assetName,
            Currency currency
    ) {
        this.id = id;
        this.assetSymbol = assetSymbol;
        this.assetName = assetName;
        this.currency = currency;;
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
    public Currency getCurrency() {
        return currency;
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
    public void setAssetCurrency(Currency currency) {
        this.currency = currency;
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Asset)) return false;
        Asset asset = (Asset) object;
        return Objects.equals(assetSymbol, asset.assetSymbol);
    }


    @Override
    public int hashCode() {
        return Objects.hash(assetSymbol);
    }


    @Override
    public String toString() {
        return "Asset{" +
                "Asset ID =" + id +
                ", Asset Symbol ='" + assetSymbol + '\'' +
                ", Asset Name ='" + assetName + '\'' +
                ", Currency ='" + currency + '}';
    }
}
