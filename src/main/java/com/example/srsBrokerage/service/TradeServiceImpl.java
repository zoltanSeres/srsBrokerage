package com.example.srsBrokerage.service;

import com.example.srsBrokerage.dto.request.trade.TradeRequest;
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

@Service
public class TradeServiceImpl implements TradeService {
    private final TradeRepository tradeRepository;
    private final TradeEntryRepository tradeEntryRepository;
    private final AccountRepository accountRepository;
    private final AssetRepository assetRepository;
    private final PositionRepository positionRepository;
    private final TradeMapper tradeMapper;

    public TradeServiceImpl(
            TradeRepository tradeRepository,
            TradeEntryRepository tradeEntryRepository,
            AccountRepository accountRepository,
            AssetRepository assetRepository,
            PositionRepository positionRepository,
            TradeMapper tradeMapper
    ) {
        this.tradeRepository = tradeRepository;
        this.tradeEntryRepository = tradeEntryRepository;
        this.accountRepository = accountRepository;
        this.assetRepository = assetRepository;
        this.positionRepository = positionRepository;
        this.tradeMapper = tradeMapper;
    }


    @Override
    @Transactional
    public TradeResponse executeTrade(TradeRequest tradeRequest) {
        Account account = accountRepository.findById(tradeRequest.accountId())
                .orElseThrow(() -> new AccountNotFoundException("Account not found."));

        Asset asset = assetRepository.findById(tradeRequest.assetId())
                .orElseThrow(() -> new AssetNotFoundException("Asset not found."));

        if (tradeRequest.quantityTraded().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTradeQuantityException("Trade quantity must be bigger than 0.");
        }

        if (tradeRequest.tradeSide() == TradeSide.BUY) {

            BigDecimal amountDebited = assetPrice.multiply(tradeRequest.quantityTraded());

            if (account.getAccountBalance().compareTo(amountDebited) < 0) {
                throw new InsufficientBalanceException("Insufficient funds.");
            }

            account.setAccountBalance(account.getAccountBalance().subtract(amountDebited));

            accountRepository.save(account);

            Trade trade = new Trade();

            trade.setQuantityTraded(tradeRequest.quantityTraded());
            trade.setTradeSide(tradeRequest.tradeSide());

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
            tradeEntryFee.setAmount(amountDebited.multiply(BigDecimal.valueOf(0.005))
                    .setScale(2, RoundingMode.HALF_UP));
            tradeEntryFee.setLedgerDirection(LedgerDirection.DEBIT);

            tradeEntryRepository.save(tradeEntryFee);

            Position position = positionRepository
                    .findByAccountAndAsset(account, asset)
                            .orElseGet(() -> createNewPosition(account, asset));

            position.setAccount(account);
            position.setAsset(asset);
            position.setHeldQuantity(position.getHeldQuantity().add(tradeRequest.quantityTraded()));
            position.setAveragePrice(PositionCalculator.calculateAveragePrice(tradeRequest.quantityTraded(), assetPrice));

            position.setHeldQuantity(position.getHeldQuantity().add(tradeRequest.quantityTraded()));

            positionRepository.save(position);

            return tradeMapper.toDto(trade);

        } else if (tradeRequest.tradeSide() == TradeSide.SELL) {

            Position position = positionRepository.findByAccountAndAsset(account, asset)
                    .orElseThrow(() -> new PositionNotFoundException("Position does not exists."));

            if (position.getHeldQuantity().compareTo(tradeRequest.quantityTraded()) < 0) {
                throw new InsufficientHeldAssetsException("Insufficient assets.");
            }

            BigDecimal amountCredited = assetPrice.multiply(tradeRequest.quantityTraded());

            account.setAccountBalance(account.getAccountBalance().add(amountCredited));

            accountRepository.save(account);

            Trade trade = new Trade();

            trade.setQuantityTraded(tradeRequest.quantityTraded());
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
            tradeEntryFee.setAmount(amountCredited.multiply(BigDecimal.valueOf(0.005))
                    .setScale(2, RoundingMode.HALF_UP));
            tradeEntryFee.setLedgerDirection(LedgerDirection.DEBIT);

            tradeEntryRepository.save(tradeEntryFee);

            position.setAccount(account);
            position.setAsset(asset);
            position.setHeldQuantity(position.getHeldQuantity().subtract(tradeRequest.quantityTraded()));
            position.setAveragePrice(PositionCalculator.calculateAveragePrice(tradeRequest.quantityTraded(), assetPrice));

            positionRepository.save(position);

            return tradeMapper.toDto(trade);
        }

        return null;
    }

    private Position createNewPosition(Account account, Asset asset) {
        Position position = new Position();

        position.setAccount(account);
        position.setAsset(asset);
        position.setHeldQuantity(BigDecimal.ZERO);
        position.setAveragePrice(BigDecimal.ZERO);

        return position;
    }


}
