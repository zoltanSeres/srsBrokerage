package com.example.srsBrokerage.service;

import com.example.srsBrokerage.enums.TradeSide;
import com.example.srsBrokerage.mapper.TradeMapper;
import com.example.srsBrokerage.model.Account;
import com.example.srsBrokerage.repository.AccountRepository;
import com.example.srsBrokerage.repository.AssetRepository;
import com.example.srsBrokerage.repository.PositionRepository;
import com.example.srsBrokerage.repository.TradeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AssetRepository assetRepository;

    @Mock
    private PositionRepository positionRepository;

    @Mock
    private TradeService tradeService;

    @Mock
    private TradeMapper tradeMapper;

    LocalDateTime timeForTesting = LocalDateTime.now();

    //used only for testing


    @Test
    void executeTrade_shouldExecuteBuyTrade() {

        Long accountId = 1L;
        String assetSymbol = "KO";
        BigDecimal quantityTraded = new BigDecimal("2");
        TradeSide tradeSide = TradeSide.BUY;
        BigDecimal assetPrice = new BigDecimal("50.00");

        Account account = TestData.accoun
    }

}
