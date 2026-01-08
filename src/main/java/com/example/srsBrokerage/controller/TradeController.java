package com.example.srsBrokerage.controller;

import com.example.srsBrokerage.dto.request.trade.TradeRequest;
import com.example.srsBrokerage.dto.response.trade.TradeResponse;
import com.example.srsBrokerage.enums.TradeSide;
import com.example.srsBrokerage.service.TradeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trades")
public class TradeController {

    private final TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @PostMapping("/execute")
    public ResponseEntity<TradeResponse> executeTrade(@Valid @RequestBody TradeRequest tradeRequest) {
        TradeResponse tradeResponse = tradeService.executeTrade(tradeRequest)   ;
        return ResponseEntity.status(HttpStatus.CREATED).body(tradeResponse);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TradeResponse>> getTradesForAccount(@PathVariable Long accountId) {
        List<TradeResponse> trades = tradeService.getTradesForAccount(accountId);
        return ResponseEntity.ok(trades);
    }
}
