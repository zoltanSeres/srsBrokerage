package com.example.srsBrokerage.service;

import com.example.srsBrokerage.dto.request.transaction.DepositRequest;
import com.example.srsBrokerage.dto.request.transaction.TransferRequest;
import com.example.srsBrokerage.dto.request.transaction.WithdrawalRequest;
import com.example.srsBrokerage.dto.response.transaction.TransactionResponse;
import com.example.srsBrokerage.enums.LedgerDirection;
import com.example.srsBrokerage.enums.TransactionType;
import com.example.srsBrokerage.exceptions.*;
import com.example.srsBrokerage.mapper.TransactionMapper;
import com.example.srsBrokerage.model.Account;
import com.example.srsBrokerage.model.Transaction;
import com.example.srsBrokerage.model.TransactionEntry;
import com.example.srsBrokerage.repository.AccountRepository;
import com.example.srsBrokerage.repository.TransactionEntryRepository;
import com.example.srsBrokerage.repository.TransactionRepository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{
    private final TransactionEntryRepository transactionEntryRepository;
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionMapper transactionMapper;

    public TransactionServiceImpl(
            TransactionEntryRepository transactionEntryRepository,
            TransactionRepository transactionRepository,
            AccountRepository accountRepository,
            TransactionMapper transactionMapper
    ) {
        this.transactionEntryRepository = transactionEntryRepository;
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.transactionMapper = transactionMapper;
    }


    @Override
    @Transactional
    public TransactionResponse deposit(DepositRequest depositRequest) {

        Account account = accountRepository.findById(depositRequest.accountId())
                .orElseThrow(() -> new AccountNotFoundException("Account not found."));

        if (depositRequest.transactionAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidDepositAmountException("Deposit must be positive.");
        }

        account.setAccountBalance(account.getAccountBalance().add(depositRequest.transactionAmount()));

        accountRepository.save(account);

        Transaction transaction = new Transaction();

        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setTransactionDescription("Deposit");

        transactionRepository.save(transaction);

        TransactionEntry transactionEntry = new TransactionEntry();

        transactionEntry.setTransaction(transaction);
        transactionEntry.setAccount(account);
        transactionEntry.setTransactionAmount(depositRequest.transactionAmount());
        transactionEntry.setTransactionCurrency(depositRequest.currency());
        transactionEntry.setLedgerDirection(LedgerDirection.CREDIT);

        transactionEntryRepository.save(transactionEntry);

        transaction.setTransactionEntries(List.of(transactionEntry));

        return transactionMapper.toDto(transaction);
    }


    @Override
    @Transactional
    public TransactionResponse withdraw(WithdrawalRequest withdrawalRequest) {
        Account account = accountRepository.findById(withdrawalRequest.accountId())
                .orElseThrow(() -> new AccountNotFoundException("Account not found."));

        if (withdrawalRequest.transactionAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidWithdrawalAmountException("Withdrawal must be positive.");
        }

        if (account.getAccountBalance().compareTo(withdrawalRequest.transactionAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient funds.");
        }

        account.setAccountBalance(account.getAccountBalance().subtract(withdrawalRequest.transactionAmount()));

        accountRepository.save(account);

        Transaction transaction = new Transaction();

        transaction.setTransactionType(TransactionType.WITHDRAWAL);
        transaction.setTransactionDescription("Withdrawal");

        transactionRepository.save(transaction);

        TransactionEntry transactionEntry = new TransactionEntry();

        transactionEntry.setTransaction(transaction);
        transactionEntry.setAccount(account);
        transactionEntry.setTransactionAmount(withdrawalRequest.transactionAmount());
        transactionEntry.setTransactionCurrency(withdrawalRequest.currency());
        transactionEntry.setLedgerDirection(LedgerDirection.DEBIT);

        transactionEntryRepository.save(transactionEntry);

        transaction.setTransactionEntries(List.of(transactionEntry));

        return transactionMapper.toDto(transaction);
    }


    @Override
    @Transactional
    public TransactionResponse transfer(TransferRequest transferRequest) {
        Account fromAccount = accountRepository.findById(transferRequest.fromAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Source account not found."));

        Account toAccount = accountRepository.findById(transferRequest.toAccountId())
                .orElseThrow(()-> new AccountNotFoundException("Destination account not found."));

        if (transferRequest.fromAccountId().equals(transferRequest.toAccountId())) {
            throw new InvalidTransferException("Source and destination account cannot be the same.");
        }

        if (transferRequest.transactionAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransferAmountException("Transfer must be positive.");
        }

        if (fromAccount.getAccountBalance().compareTo(transferRequest.transactionAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient funds in source account.");
        }

        fromAccount.setAccountBalance(fromAccount.getAccountBalance().subtract(transferRequest.transactionAmount()));
        toAccount.setAccountBalance(toAccount.getAccountBalance().add(transferRequest.transactionAmount()));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction transaction = new Transaction();

        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setTransactionDescription("Transfer");

        transactionRepository.save(transaction);

        TransactionEntry transactionEntryDebit = new TransactionEntry();

        transactionEntryDebit.setTransaction(transaction);
        transactionEntryDebit.setAccount(fromAccount);
        transactionEntryDebit.setTransactionAmount(transferRequest.transactionAmount());
        transactionEntryDebit.setTransactionCurrency(transferRequest.currency());
        transactionEntryDebit.setLedgerDirection(LedgerDirection.DEBIT);

        transactionEntryRepository.save(transactionEntryDebit);

        TransactionEntry transactionEntryCredit = new TransactionEntry();

        transactionEntryCredit.setTransaction(transaction);
        transactionEntryCredit.setAccount(toAccount);
        transactionEntryCredit.setTransactionAmount(transferRequest.transactionAmount());
        transactionEntryCredit.setTransactionCurrency(transferRequest.currency());
        transactionEntryCredit.setLedgerDirection(LedgerDirection.CREDIT);

        transactionEntryRepository.save(transactionEntryCredit);

        transaction.setTransactionEntries(List.of(transactionEntryDebit, transactionEntryCredit));

        return transactionMapper.toDto(transaction);
    }


    @Override
    public List<TransactionResponse> getTransactionsForAccount(Long accountId) {
        List<Transaction> transactions =
                transactionRepository.findDistinctByTransactionEntries_Account_Id(accountId);

        return transactions.stream()
                .map(transactionMapper::toDto)
                .toList();
    }
}
