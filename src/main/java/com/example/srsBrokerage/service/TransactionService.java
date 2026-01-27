package com.example.srsBrokerage.service;

import com.example.srsBrokerage.dto.request.transaction.DepositRequest;
import com.example.srsBrokerage.dto.request.transaction.TransferRequest;
import com.example.srsBrokerage.dto.request.transaction.WithdrawalRequest;
import com.example.srsBrokerage.dto.response.transaction.TransactionResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface TransactionService {
    TransactionResponse deposit(DepositRequest depositRequest, Authentication authentication);
    TransactionResponse withdraw(WithdrawalRequest withdrawalRequest, Authentication authentication);
    TransactionResponse transfer(TransferRequest transferRequest, Authentication authentication);
    List<TransactionResponse> getTransactionsForAccount(Long AccountId, Authentication authentication);
}
