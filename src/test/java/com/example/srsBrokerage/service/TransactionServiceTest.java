package com.example.srsBrokerage.service;

import com.example.srsBrokerage.dto.request.transaction.DepositRequest;
import com.example.srsBrokerage.dto.response.transaction.TransactionEntryResponse;
import com.example.srsBrokerage.dto.response.transaction.TransactionResponse;
import com.example.srsBrokerage.enums.AccountCurrency;
import com.example.srsBrokerage.enums.AccountType;
import com.example.srsBrokerage.enums.EntryType;
import com.example.srsBrokerage.enums.TransactionType;
import com.example.srsBrokerage.mapper.TransactionMapper;
import com.example.srsBrokerage.model.Account;
import com.example.srsBrokerage.model.Transaction;
import com.example.srsBrokerage.model.TransactionEntry;
import com.example.srsBrokerage.repository.AccountRepository;
import com.example.srsBrokerage.repository.TransactionEntryRepository;
import com.example.srsBrokerage.repository.TransactionRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionEntryRepository transactionEntryRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    LocalDateTime timeForTesting = LocalDateTime.now();


    @Test
    void deposit_shouldCreateTransaction_whenValidInput() {

        Account account = new Account(
                1L,
                10L,
                AccountType.CHECKING,
                new BigDecimal("1000.00"),
                AccountCurrency.USD,
                timeForTesting,
                timeForTesting
        );

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        DepositRequest request = new DepositRequest(
                1L,
                new BigDecimal("300.00"),
                AccountCurrency.USD,
                "Deposit"
        );

        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        TransactionEntryResponse entryResponse = new TransactionEntryResponse(
                1L,
                new BigDecimal("300.00"),
                AccountCurrency.USD,
                EntryType.CREDIT
        );

        TransactionResponse response = new TransactionResponse(
                1L,
                TransactionType.DEPOSIT,
                "Deposit",
                timeForTesting,
                List.of(entryResponse)
        );

        when(transactionMapper.toDto(any(Transaction.class)))
                .thenReturn(response);

        TransactionResponse result = transactionService.deposit(request);

        assertEquals(new BigDecimal("1300.00"), account.getAccountBalance());

        verify(accountRepository).findById(1L);
        verify(accountRepository).save(account);
        verify(transactionRepository).save(any(Transaction.class));

    }


}
