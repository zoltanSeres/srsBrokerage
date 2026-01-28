package com.example.srsBrokerage.controller;

import com.example.srsBrokerage.dto.request.trade.TradeRequest;
import com.example.srsBrokerage.dto.response.trade.TradeResponse;
import com.example.srsBrokerage.service.TradeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class TradeController {

    private final TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @PostMapping("/accounts/{accountId}/trades")
    public ResponseEntity<TradeResponse> executeTrade(
            @Valid @RequestBody TradeRequest tradeRequest,
            Authentication authentication
    ) {
        TradeResponse tradeResponse = tradeService.executeTrade(tradeRequest, authentication)   ;
        return ResponseEntity.status(HttpStatus.CREATED).body(tradeResponse);
    }

    @GetMapping("/trades")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TradeResponse>> getTradesForAccount(@PathVariable Long accountId) {
        List<TradeResponse> trades = tradeService.getTradesForAccount(accountId);
        return ResponseEntity.ok(trades);
    }
}
