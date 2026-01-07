package com.example.srsBrokerage.mapper;

import com.example.srsBrokerage.dto.response.trade.TradeEntryResponse;
import com.example.srsBrokerage.enums.LedgerDirection;
import com.example.srsBrokerage.enums.TradeEntryType;
import com.example.srsBrokerage.model.Trade;
import com.example.srsBrokerage.model.TradeEntry;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TradeEntryMapper {

    public TradeEntry toEntity(
            Long accountId,
            Long assetId,
            Trade trade,
            TradeEntryType tradeEntryType,
            BigDecimal amount,
            LedgerDirection ledgerDirection
    ) {
        TradeEntry tradeEntry = new TradeEntry();

        tradeEntry.setAccountId(accountId);
        tradeEntry.setAssetId(assetId);
        tradeEntry.setTrade(trade);
        tradeEntry.setTradeEntryType(tradeEntryType);
        tradeEntry.setAmount(amount);
        tradeEntry.setLedgerDirection(ledgerDirection);

        return tradeEntry;
    }

    public TradeEntryResponse toDto(TradeEntry tradeEntry) {
        return new TradeEntryResponse(
                tradeEntry.getId(),
                tradeEntry.getAccountId(),
                tradeEntry.getAssetId(),
                tradeEntry.getTradeEntryType(),
                tradeEntry.getAmount(),
                tradeEntry.getLedgerDirection(),
                tradeEntry.getCreatedAt()
        );
    }


    public List<TradeEntryResponse> toDtoList(List<TradeEntry> tradeEntries) {
        return tradeEntries.stream().
                map(this::toDto)
                .collect(Collectors.toList());
    }
}
