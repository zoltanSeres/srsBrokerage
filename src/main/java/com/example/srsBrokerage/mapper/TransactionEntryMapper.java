package com.example.srsBrokerage.mapper;

import com.example.srsBrokerage.dto.request.transaction.DepositRequest;
import com.example.srsBrokerage.dto.request.transaction.TransferRequest;
import com.example.srsBrokerage.dto.request.transaction.WithdrawalRequest;
import com.example.srsBrokerage.dto.response.transaction.TransactionEntryResponse;
import com.example.srsBrokerage.enums.LedgerDirection;
import com.example.srsBrokerage.model.Account;
import com.example.srsBrokerage.model.Transaction;
import com.example.srsBrokerage.model.TransactionEntry;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionEntryMapper {

    public TransactionEntry depositToEntity(
            DepositRequest depositRequest,
            Transaction transaction,
            Account account
    ) {

        TransactionEntry depositTransactionEntry = new TransactionEntry();

        depositTransactionEntry.setAccount(account);
        depositTransactionEntry.setTransactionAmount(depositRequest.transactionAmount());
        depositTransactionEntry.setTransactionCurrency(depositRequest.currency());
        depositTransactionEntry.setTransaction(transaction);
        depositTransactionEntry.setLedgerDirection(LedgerDirection.CREDIT);

        return depositTransactionEntry;
    }

    public TransactionEntry withdrawalToEntity(
            WithdrawalRequest withdrawalRequest,
            Transaction transaction,
            Account account
    ) {
        TransactionEntry withdrawalTransactionEntry = new TransactionEntry();

        withdrawalTransactionEntry.setAccount(account);
        withdrawalTransactionEntry.setTransactionAmount(withdrawalRequest.transactionAmount());
        withdrawalTransactionEntry.setTransactionCurrency(withdrawalRequest.currency());
        withdrawalTransactionEntry.setTransaction(transaction);
        withdrawalTransactionEntry.setLedgerDirection(LedgerDirection.DEBIT);

        return withdrawalTransactionEntry;
    }

    public List<TransactionEntry> transferToEntity(
            TransferRequest transferRequest,
            Transaction transaction,
            Account fromAccount,
            Account toAccount
    ) {
        TransactionEntry transferDebit = new TransactionEntry();

        transferDebit.setAccount(fromAccount);
        transferDebit.setTransactionAmount(transferRequest.transactionAmount());
        transferDebit.setTransactionCurrency(transferRequest.currency());
        transferDebit.setLedgerDirection(LedgerDirection.DEBIT);

        TransactionEntry transferCredit = new TransactionEntry();

        transferCredit.setAccount(toAccount);
        transferCredit.setTransactionAmount(transferRequest.transactionAmount());
        transferCredit.setTransactionCurrency(transferRequest.currency());
        transferCredit.setLedgerDirection(LedgerDirection.CREDIT);

        return List.of(transferDebit, transferCredit);
    }

    public TransactionEntryResponse toDto(TransactionEntry transactionEntry) {

        return new TransactionEntryResponse(
                transactionEntry.getAccount().getId(),
                transactionEntry.getTransactionAmount(),
                transactionEntry.getTransactionCurrency(),
                transactionEntry.getLedgerDirection()
        );
    }

    public List<TransactionEntryResponse> toDtoList(List<TransactionEntry> transactionEntries) {
        return transactionEntries.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
