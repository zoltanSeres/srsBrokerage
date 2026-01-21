package com.example.srsBrokerage.service;

import com.example.srsBrokerage.client.MarketDataClient;
import com.example.srsBrokerage.dto.request.trade.TradeRequest;
import com.example.srsBrokerage.dto.response.asset.ExternalAssetResponse;
import com.example.srsBrokerage.dto.response.trade.TradeResponse;
import com.example.srsBrokerage.enums.LedgerDirection;
import com.example.srsBrokerage.enums.TradeEntryType;
import com.example.srsBrokerage.enums.TradeSide;
import com.example.srsBrokerage.exceptions.*;
import com.example.srsBrokerage.mapper.TradeMapper;
import com.example.srsBrokerage.model.*;
import com.example.srsBrokerage.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class TradeServiceImpl implements TradeService {
    private final TradeRepository tradeRepository;
    private final TradeEntryRepository tradeEntryRepository;
    private final AccountRepository accountRepository;
    private final AssetRepository assetRepository;
    private final PositionRepository positionRepository;
    private final TradeMapper tradeMapper;
    private final MarketDataClient marketDataClient;

    public TradeServiceImpl(
            TradeRepository tradeRepository,
            TradeEntryRepository tradeEntryRepository,
            AccountRepository accountRepository,
            AssetRepository assetRepository,
            PositionRepository positionRepository,
            TradeMapper tradeMapper,
            MarketDataClient marketDataClient

    ) {
        this.tradeRepository = tradeRepository;
        this.tradeEntryRepository = tradeEntryRepository;
        this.accountRepository = accountRepository;
        this.assetRepository = assetRepository;
        this.positionRepository = positionRepository;
        this.tradeMapper = tradeMapper;
        this.marketDataClient = marketDataClient;
    }


    @Override
    @Transactional
    public TradeResponse executeTrade(TradeRequest tradeRequest) {
        Account account = accountRepository.findById(tradeRequest.accountId())
                .orElseThrow(() -> new AccountNotFoundException("Account not found."));

        Asset asset = assetRepository.findByAssetSymbol(tradeRequest.assetSymbol())
                .orElseThrow(() -> new AssetNotFoundException("Asset not found."));

        if (tradeRequest.quantityTraded().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTradeQuantityException("Trade quantity must be bigger than 0.");
        }

        if (tradeRequest.tradeSide() == TradeSide.BUY) {

            ExternalAssetResponse externalAssetResponse = marketDataClient.getAssetData(tradeRequest.assetSymbol());

            BigDecimal amountDebited = externalAssetResponse.assetPrice().multiply(tradeRequest.quantityTraded());

            if (account.getAccountBalance().compareTo(amountDebited) < 0) {
                throw new InsufficientBalanceException("Insufficient funds.");
            }

            accountRepository.save(account);

            Trade trade = new Trade();

            trade.setTradeSide(TradeSide.BUY);

            tradeRepository.save(trade);

            TradeEntry tradeEntryCash = new TradeEntry();

            tradeEntryCash.setTrade(trade);
            tradeEntryCash.setAccountId(account.getId());
            tradeEntryCash.setAssetId(asset.getId());
            tradeEntryCash.setTradeEntryType(TradeEntryType.CASH);
            tradeEntryCash.setAmount(amountDebited);
            tradeEntryCash.setLedgerDirection(LedgerDirection.DEBIT);

            tradeEntryRepository.save(tradeEntryCash);


            TradeEntry tradeEntryAsset = new TradeEntry();

            tradeEntryAsset.setTrade(trade);
            tradeEntryAsset.setAccountId(account.getId());
            tradeEntryAsset.setAssetId(asset.getId());
            tradeEntryAsset.setTradeEntryType(TradeEntryType.ASSET);
            tradeEntryAsset.setAmount(tradeRequest.quantityTraded());
            tradeEntryAsset.setLedgerDirection(LedgerDirection.CREDIT);

            tradeEntryRepository.save(tradeEntryAsset);


            TradeEntry tradeEntryFee = new TradeEntry();

            tradeEntryFee.setTrade(trade);
            tradeEntryFee.setAccountId(account.getId());
            tradeEntryFee.setAssetId(asset.getId());
            tradeEntryFee.setTradeEntryType(TradeEntryType.FEE);
            tradeEntryFee.setAmount(amountDebited.multiply(BigDecimal.valueOf(0.05))
                    .setScale(2, RoundingMode.HALF_UP));
            tradeEntryFee.setLedgerDirection(LedgerDirection.DEBIT);

            account.setAccountBalance(account.getAccountBalance().subtract(tradeEntryFee.getAmount()));

            tradeEntryRepository.save(tradeEntryFee);

            trade.setTradeEntries(List.of(tradeEntryCash, tradeEntryAsset, tradeEntryFee));

            Position position = positionRepository
                    .findByAccountIdAndAssetId(account.getId(), asset.getId())
                            .orElseGet(() -> createNewPosition(account, asset));

            position.setAccount(account);
            position.setAssetId(asset.getId());
            position.setHeldQuantity(position.getHeldQuantity().add(tradeRequest.quantityTraded()));

            BigDecimal newAveragePrice;

            if (position.getHeldQuantity().compareTo(BigDecimal.ZERO) == 0) {
                newAveragePrice = TradeCalculationService.initialAveragePrice(externalAssetResponse.assetPrice());
            } else {
                newAveragePrice = TradeCalculationService.weightedAveragePrice(
                        position.getAveragePrice(),
                        position.getHeldQuantity().subtract(tradeRequest.quantityTraded()), //get quantity before adding the new quantity
                        externalAssetResponse.assetPrice(),
                        tradeRequest.quantityTraded()
                );
            }
            position.setAveragePrice(newAveragePrice);

            positionRepository.save(position);

            return tradeMapper.toDto(trade);

        } else if (tradeRequest.tradeSide() == TradeSide.SELL) {

            Position position = positionRepository.findByAccountIdAndAssetId(account.getId(), asset.getId())
                    .orElseThrow(() -> new PositionNotFoundException("Position does not exists."));

            if (position.getHeldQuantity().compareTo(tradeRequest.quantityTraded()) < 0) {
                throw new InsufficientHeldAssetsException("Insufficient assets.");
            }

            ExternalAssetResponse externalAssetResponse = marketDataClient.getAssetData(tradeRequest.assetSymbol());

            BigDecimal amountCredited = externalAssetResponse.assetPrice().multiply(tradeRequest.quantityTraded());

            accountRepository.save(account);

            Trade trade = new Trade();

            trade.setTradeSide(TradeSide.SELL);

            tradeRepository.save(trade);

            TradeEntry tradeEntryCash = new TradeEntry();

            tradeEntryCash.setTrade(trade);
            tradeEntryCash.setAccountId(account.getId());
            tradeEntryCash.setAssetId(asset.getId());
            tradeEntryCash.setTradeEntryType(TradeEntryType.CASH);
            tradeEntryCash.setAmount(amountCredited);
            tradeEntryCash.setLedgerDirection(LedgerDirection.CREDIT);

            tradeEntryRepository.save(tradeEntryCash);

            TradeEntry tradeEntryAsset = new TradeEntry();

            tradeEntryAsset.setTrade(trade);
            tradeEntryAsset.setAccountId(account.getId());
            tradeEntryAsset.setAssetId(asset.getId());
            tradeEntryAsset.setTradeEntryType(TradeEntryType.ASSET);
            tradeEntryAsset.setAmount(tradeRequest.quantityTraded());
            tradeEntryAsset.setLedgerDirection(LedgerDirection.DEBIT);

            tradeEntryRepository.save(tradeEntryAsset);

            TradeEntry tradeEntryFee = new TradeEntry();

            tradeEntryFee.setTrade(trade);
            tradeEntryFee.setAccountId(account.getId());
            tradeEntryFee.setAssetId(asset.getId());
            tradeEntryFee.setTradeEntryType(TradeEntryType.FEE);
            tradeEntryFee.setAmount(amountCredited.multiply(BigDecimal.valueOf(0.05))
                    .setScale(2, RoundingMode.HALF_UP));
            tradeEntryFee.setLedgerDirection(LedgerDirection.DEBIT);

            tradeEntryRepository.save(tradeEntryFee);

            account.setAccountBalance(account.getAccountBalance().subtract(tradeEntryFee.getAmount()));

            trade.setTradeEntries(List.of(tradeEntryCash, tradeEntryAsset, tradeEntryFee));

            position.setAccount(account);
            position.setAssetId(asset.getId());
            position.setHeldQuantity(position.getHeldQuantity().subtract(tradeRequest.quantityTraded()));

            // no setAveragePrice: average price does not change when selling

            positionRepository.save(position);

            return tradeMapper.toDto(trade);
        }

        return null;
    }

    private Position createNewPosition(Account account, Asset asset) {
        Position position = new Position();

        position.setAccount(account);
        position.setAssetId(asset.getId());
        position.setHeldQuantity(BigDecimal.ZERO);
        position.setAveragePrice(BigDecimal.ZERO);

        return position;
    }

    @Override
    public List<TradeResponse> getTradesForAccount(Long accountId) {
        List<Trade> trades = tradeRepository.findAllByTradeEntriesAccountId(accountId);
        return trades.stream()
                .map(tradeMapper::toDto)
                .toList();
    }
}
