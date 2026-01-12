package com.example.srsBrokerage.mapper;

import com.example.srsBrokerage.dto.request.trade.TradeRequest;
import com.example.srsBrokerage.dto.response.trade.TradeEntryResponse;
import com.example.srsBrokerage.dto.response.trade.TradeResponse;
import com.example.srsBrokerage.model.Trade;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TradeMapper {
    public Trade toEntity(TradeRequest tradeRequest) {
        Trade trade = new Trade();

        trade.setTradeSide(tradeRequest.tradeSide());

        return trade;
    }


    public TradeResponse toDto(Trade trade) {
        List<TradeEntryResponse> entryResponses = trade.getTradeEntries().stream()
                .map(tradeEntry -> new TradeEntryResponse(
                        tradeEntry.getId(),
                        tradeEntry.getAccountId(),
                        tradeEntry.getAssetId(),
                        tradeEntry.getTradeEntryType(),
                        tradeEntry.getAmount(),
                        tradeEntry.getLedgerDirection(),
                        tradeEntry.getCreatedAt()
                ))
                .collect(Collectors.toList());
        return new TradeResponse(
                trade.getId(),
                trade.getTradeSide(),
                entryResponses,
                trade.getCreatedAt()
        );
    }


    public List<TradeResponse> toDtoList(List<Trade>trades) {
        return trades.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
