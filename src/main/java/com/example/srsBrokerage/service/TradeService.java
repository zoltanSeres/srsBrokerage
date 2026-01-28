package com.example.srsBrokerage.service;

import com.example.srsBrokerage.dto.request.trade.TradeRequest;
import com.example.srsBrokerage.dto.response.trade.TradeResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface TradeService {
    TradeResponse executeTrade(TradeRequest tradeRequest, Authentication authentication);
    List<TradeResponse> getTradesForAccount(Long accountId);
}
