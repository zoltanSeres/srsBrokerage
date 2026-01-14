package com.example.srsBrokerage.testdata;


import com.example.srsBrokerage.enums.TradeEntryType;
import com.example.srsBrokerage.enums.TradeSide;
import com.example.srsBrokerage.model.*;
import org.springframework.security.access.expression.AbstractSecurityExpressionHandler;

import java.math.BigDecimal;
import java.util.ArrayList;

public class TestData {

    private TestData() {}

    public static Account accountWithBalance(String balance) {
        Account account = new Account();
        account.setId(1L);
        account.setAccountBalance(new BigDecimal(balance));
        return account;
    }

    public static Asset asset(String symbol) {
        Asset asset = new Asset();
        asset.setId(1L);
        asset.setAssetSymbol(symbol);
        return asset;
    }

    public static Position position(Account account, Long assetId, BigDecimal quantity) {
        Position position = new Position();
        position.setId(1L);
        position.setAccount(account);
        position.setAssetId(assetId);
        position.setHeldQuantity(quantity);
        return position;
    }

    public static Trade trade(TradeSide tradeSide) {
        Trade trade = new Trade();
        trade.setTradeId(1L);
        trade.setTradeSide(tradeSide);
        trade.setTradeEntries(new ArrayList<>());
        return trade;
    }

    public static TradeEntry cashEntry(Trade trade, BigDecimal amount) {
       TradeEntry tradeEntry = new TradeEntry();
       tradeEntry.setTrade(trade);
       tradeEntry.setTradeEntryType(TradeEntryType.CASH);
       tradeEntry.setAmount(amount);
       return tradeEntry;
    }

    public static TradeEntry assetEntry(Trade trade, BigDecimal amount) {
        TradeEntry tradeEntry = new TradeEntry();
        tradeEntry.setTrade(trade);
        tradeEntry.setTradeEntryType(TradeEntryType.ASSET);
        tradeEntry.setAmount(amount);
        return tradeEntry;
    }

    public static TradeEntry feeEntry(Trade trade, BigDecimal amount) {
        TradeEntry tradeEntry = new TradeEntry();
        tradeEntry.setTrade(trade);
        tradeEntry.setTradeEntryType(TradeEntryType.FEE);
        tradeEntry.setAmount(amount);
        return tradeEntry;
    }
}
