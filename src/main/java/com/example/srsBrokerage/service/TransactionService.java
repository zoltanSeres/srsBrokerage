package com.example.srsBrokerage.service;

import com.example.srsBrokerage.dto.request.transaction.DepositRequest;
import com.example.srsBrokerage.dto.request.transaction.TransferRequest;
import com.example.srsBrokerage.dto.request.transaction.WithdrawalRequest;
import com.example.srsBrokerage.dto.response.transaction.TransactionResponse;

import java.util.List;

public interface TransactionService {
    TransactionResponse deposit(DepositRequest depositRequest);
    TransactionResponse withdraw(WithdrawalRequest withdrawalRequest);
    TransactionResponse transfer(TransferRequest transferRequest);
    List<TransactionResponse> getTransactionsForAccount(Long AccountId);
}
