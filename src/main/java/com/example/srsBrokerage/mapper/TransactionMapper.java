package com.example.srsBrokerage.mapper;

import com.example.srsBrokerage.dto.request.transaction.DepositRequest;
import com.example.srsBrokerage.dto.request.transaction.WithdrawalRequest;
import com.example.srsBrokerage.dto.response.transaction.TransactionResponse;
import com.example.srsBrokerage.model.Account;
import com.example.srsBrokerage.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionMapper {
    public Transaction depositToEntity(DepositRequest depositRequest, Account account) {
        Transaction transaction = new Transaction();

        transaction.setAccount(account);
        transaction.setTransactionAmount(depositRequest.transactionAmount());
        transaction.setTransactionDescription(depositRequest.transactionDescription());

        return transaction;
    }

    public Transaction withdrawalToEntity(WithdrawalRequest withdrawalRequest, Account account) {
        Transaction transaction = new Transaction();

        transaction.setAccount(account);
        transaction.setTransactionAmount(withdrawalRequest.transactionAmount());
        transaction.setTransactionDescription(withdrawalRequest.transactionDescription());

        return transaction;
    }

    public TransactionResponse toDto(Transaction transaction) {

        return new TransactionResponse(
                transaction.getId(),
                transaction.getTransactionType(),
                transaction.getAccount().getId(),
                transaction.getTransactionAmount(),
                transaction.getTransactionDescription(),
                transaction.getCreatedAt()
        );
    }

    public List<TransactionResponse> toDtoList(List<Transaction> transactions) {
        return transactions.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}

