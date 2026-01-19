package com.example.srsBrokerage.service;

import com.example.srsBrokerage.client.MarketDataClient;
import com.example.srsBrokerage.dto.request.trade.TradeRequest;
import com.example.srsBrokerage.dto.response.asset.ExternalAssetResponse;
import com.example.srsBrokerage.enums.TradeSide;
import com.example.srsBrokerage.exceptions.*;
import com.example.srsBrokerage.mapper.TradeMapper;
import com.example.srsBrokerage.model.*;
import com.example.srsBrokerage.repository.*;
import com.example.srsBrokerage.testdata.TestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;

    @Mock
    private TradeEntryRepository tradeEntryRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AssetRepository assetRepository;

    @Mock
    private PositionRepository positionRepository;

    @Mock
    private TradeMapper tradeMapper;

    @Mock
    private MarketDataClient marketDataClient;

    @InjectMocks
    private TradeServiceImpl tradeService;

    @Test
    void executeTrade_shouldExecuteBuyTrade() {

        Account account = TestData.accountWithBalance("1000");
        Asset asset = TestData.asset("KO");
        Position position = TestData.position(account, asset.getId(), new BigDecimal("2"));
        position.setAveragePrice(new BigDecimal("40"));
        Trade trade = TestData.trade(TradeSide.BUY);

        when(accountRepository.findById(1L)).
                thenReturn(Optional.of(account));
        when(assetRepository.findByAssetSymbol("KO")).
                thenReturn(Optional.of(asset));
        when(positionRepository.findByAccountIdAndAssetId(account.getId(), asset.getId())).
                thenReturn(Optional.of(position));
        when(tradeEntryRepository.save(any()))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        TradeRequest tradeRequest = new TradeRequest(
                account.getId(),
                asset.getAssetSymbol(),
                new BigDecimal("2"),
                TradeSide.BUY
        );

        when(marketDataClient.getAssetData("KO"))
                .thenReturn(new ExternalAssetResponse("KO", new BigDecimal("70")));

        tradeService.executeTrade(tradeRequest);

        verify(tradeEntryRepository, times(3)).save(any());

        assertEquals(new BigDecimal("4"), position.getHeldQuantity());
        assertEquals(new BigDecimal("853.00"), account.getAccountBalance());

        verify(tradeRepository).save(any(Trade.class));
        verify(positionRepository).save(position);
    }


    @Test
    void executeTrade_shouldThrowException_whenAccountNotFound() {
        TradeRequest tradeRequest = new TradeRequest(
                1L,
                "KO",
                new BigDecimal("1"),
                TradeSide.BUY
        );

        when(accountRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class,
                () -> tradeService.executeTrade(tradeRequest));

        verifyNoInteractions(tradeRepository, tradeEntryRepository, positionRepository);
    }


    @Test
    void executeTrade_shouldThrowException_whenAssetNotFound() {
        TradeRequest tradeRequest = new TradeRequest(
                1L,
                "KO",
                new BigDecimal("1"),
                TradeSide.BUY
        );

        Account account = TestData.accountWithBalance("1000");

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        when(assetRepository.findByAssetSymbol("KO"))
                .thenReturn(Optional.empty());

        assertThrows(AssetNotFoundException.class,
                () -> tradeService.executeTrade(tradeRequest));

        verifyNoInteractions(tradeRepository, tradeEntryRepository, positionRepository);
    }


    @Test
    void executeTrade_shouldThrowException_whenInsufficientFunds() {
        Account account = TestData.accountWithBalance("22");
        Asset asset = TestData.asset("KO");

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));
        when(assetRepository.findByAssetSymbol("KO"))
                .thenReturn(Optional.of(asset));
        when(marketDataClient.getAssetData("KO"))
                .thenReturn(new ExternalAssetResponse("KO", new BigDecimal("80")));

        TradeRequest tradeRequest = new TradeRequest(
                1L,
                "KO",
                new BigDecimal("10"),
                TradeSide.BUY
        );

        assertThrows(InsufficientBalanceException.class,
                () -> tradeService.executeTrade(tradeRequest));

        verifyNoInteractions(tradeRepository, tradeEntryRepository, positionRepository);
    }


    @Test
    void executeTrade_shouldThrowException_whenInvalidTradeQuantity() {

        Account account = TestData.accountWithBalance("1100");
        Asset asset = TestData.asset("KO");

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));
        when(assetRepository.findByAssetSymbol("KO"))
                .thenReturn(Optional.of(asset));

        TradeRequest tradeRequest = new TradeRequest(
                1L,
                "KO",
                new BigDecimal("0"),
                TradeSide.BUY
        );

        assertThrows(InvalidTradeQuantityException.class,
                () -> tradeService.executeTrade(tradeRequest));

        verifyNoInteractions(
                tradeRepository,
                tradeEntryRepository,
                positionRepository
        );
    }


    @Test
    void executeTrade_shouldThrowException_whenMarketDataUnavailable() {
        Account account = TestData.accountWithBalance("500");
        Asset asset = TestData.asset("KO");

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));
        when(assetRepository.findByAssetSymbol("KO"))
                .thenReturn(Optional.of(asset));
        when(marketDataClient.getAssetData("KO"))
                .thenThrow(new AssetDataFetchException("API down."));

        TradeRequest tradeRequest = new TradeRequest(
                1L,
                "KO",
                new BigDecimal("2"),
                TradeSide.BUY
        );

        assertThrows(AssetDataFetchException.class,
                () -> tradeService.executeTrade(tradeRequest));

        verifyNoInteractions(
                tradeRepository,
                tradeEntryRepository,
                positionRepository
        );
    }
}
