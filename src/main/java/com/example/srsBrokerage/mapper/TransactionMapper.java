package com.example.srsBrokerage.mapper;

import com.example.srsBrokerage.dto.request.transaction.DepositRequest;
import com.example.srsBrokerage.dto.request.transaction.TransferRequest;
import com.example.srsBrokerage.dto.request.transaction.WithdrawalRequest;
import com.example.srsBrokerage.dto.response.transaction.TransactionEntryResponse;
import com.example.srsBrokerage.dto.response.transaction.TransactionResponse;
import com.example.srsBrokerage.enums.TransactionType;
import com.example.srsBrokerage.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionMapper {

    public Transaction depositToEntity(DepositRequest depositRequest) {
        Transaction depositTransaction = new Transaction();

        depositTransaction.setTransactionType(TransactionType.DEPOSIT);
        depositTransaction.setTransactionDescription(depositRequest.transactionDescription());

        return depositTransaction;
    }

    public Transaction withdrawalToEntity(WithdrawalRequest withdrawalRequest) {
        Transaction withdrawalTransaction = new Transaction();

        withdrawalTransaction.setTransactionType(TransactionType.WITHDRAWAL);
        withdrawalTransaction.setTransactionDescription(withdrawalRequest.transactionDescription());

        return withdrawalTransaction;
    }

    public Transaction transferToEntity(TransferRequest transferRequest) {
        Transaction transferTransaction = new Transaction();

        transferTransaction.setTransactionType(TransactionType.TRANSFER);
        transferTransaction.setTransactionDescription(transferRequest.transactionDescription());

        return transferTransaction;
    }

    public TransactionResponse toDto(Transaction transaction) {

        List<TransactionEntryResponse> entryResponses = transaction.getTransactionEntries().stream()
                .map(transactionEntry -> new TransactionEntryResponse(
                        transactionEntry.getAccount().getId(),
                        transactionEntry.getTransactionAmount(),
                        transactionEntry.getTransactionCurrency(),
                        transactionEntry.getLedgerDirection()
                ))
                .collect(Collectors.toList());

        return new TransactionResponse(
                transaction.getId(),
                transaction.getTransactionType(),
                transaction.getTransactionDescription(),
                transaction.getCreatedAt(),
                entryResponses
        );
    }

    public List<TransactionResponse> toDtoList(List<Transaction> transactions) {
        return transactions.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}

