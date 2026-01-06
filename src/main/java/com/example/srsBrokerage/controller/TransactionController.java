package com.example.srsBrokerage.controller;

import com.example.srsBrokerage.dto.request.transaction.DepositRequest;
import com.example.srsBrokerage.dto.request.transaction.TransferRequest;
import com.example.srsBrokerage.dto.request.transaction.WithdrawalRequest;
import com.example.srsBrokerage.dto.response.transaction.TransactionResponse;
import com.example.srsBrokerage.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponse> deposit(@Valid @RequestBody DepositRequest depositRequest) {
        TransactionResponse transactionResponse = transactionService.deposit(depositRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionResponse);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(@Valid @RequestBody WithdrawalRequest withdrawalRequest) {
        TransactionResponse transactionResponse = transactionService.withdraw(withdrawalRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionResponse);
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transfer(@Valid @RequestBody TransferRequest transferRequest) {
        TransactionResponse transactionResponse = transactionService.transfer(transferRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionResponse);
    }
}
