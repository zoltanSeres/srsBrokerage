package com.example.srsBrokerage.service;

import com.example.srsBrokerage.dto.request.transaction.DepositRequest;
import com.example.srsBrokerage.dto.response.transaction.TransactionResponse;
import com.example.srsBrokerage.enums.EntryType;
import com.example.srsBrokerage.enums.TransactionType;
import com.example.srsBrokerage.exceptions.AccountNotFoundException;
import com.example.srsBrokerage.exceptions.InvalidDepositAmountException;
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

        Transaction transaction = new Transaction();

        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setTransactionDescription("Deposit");

        transactionRepository.save(transaction);

        TransactionEntry transactionEntry = new TransactionEntry();

        transactionEntry.setTransaction(transaction);
        transactionEntry.setAccount(account);
        transactionEntry.setTransactionAmount(depositRequest.transactionAmount());
        transactionEntry.setTransactionCurrency(depositRequest.currency());
        transactionEntry.setEntryType(EntryType.CREDIT);

        transactionEntryRepository.save(transactionEntry);

        return transactionMapper.toDto(transaction);
    }


}
