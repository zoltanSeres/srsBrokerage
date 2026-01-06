package com.example.srsBrokerage.service;

import com.example.srsBrokerage.dto.request.transaction.DepositRequest;
import com.example.srsBrokerage.dto.request.transaction.TransferRequest;
import com.example.srsBrokerage.dto.request.transaction.WithdrawalRequest;
import com.example.srsBrokerage.dto.response.transaction.TransactionEntryResponse;
import com.example.srsBrokerage.dto.response.transaction.TransactionResponse;
import com.example.srsBrokerage.enums.AccountCurrency;
import com.example.srsBrokerage.enums.AccountType;
import com.example.srsBrokerage.enums.EntryType;
import com.example.srsBrokerage.enums.TransactionType;
import com.example.srsBrokerage.exceptions.AccountNotFoundException;
import com.example.srsBrokerage.exceptions.InsufficientBalanceException;
import com.example.srsBrokerage.exceptions.InvalidDepositAmountException;
import com.example.srsBrokerage.exceptions.InvalidWithdrawalAmountException;
import com.example.srsBrokerage.mapper.TransactionMapper;
import com.example.srsBrokerage.model.Account;
import com.example.srsBrokerage.model.Transaction;
import com.example.srsBrokerage.repository.AccountRepository;
import com.example.srsBrokerage.repository.TransactionEntryRepository;
import com.example.srsBrokerage.repository.TransactionRepository;

import org.checkerframework.checker.units.qual.A;
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


    @Test
    void withdraw_shouldCreateTransaction_whenValidInput() {
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

        WithdrawalRequest request = new WithdrawalRequest(
                1L,
                new BigDecimal("200.00"),
                AccountCurrency.USD,
                "Withdraw"
        );

        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        TransactionEntryResponse entryResponse = new TransactionEntryResponse(
                1L,
                new BigDecimal("200.00"),
                AccountCurrency.USD,
                EntryType.DEBIT
        );

        TransactionResponse response = new TransactionResponse(
                1L,
                TransactionType.WITHDRAWAL,
                "Withdraw",
                timeForTesting,
                List.of(entryResponse)
        );

        when(transactionMapper.toDto(any(Transaction.class)))
                .thenReturn(response);

        TransactionResponse result = transactionService.withdraw(request);

        assertEquals(new BigDecimal("800.00"), account.getAccountBalance());

        verify(accountRepository).findById(1L);
        verify(accountRepository).save(account);
        verify(transactionRepository).save(any(Transaction.class));
    }


    @Test
    void transfer_shouldCreateTransaction_whenValidInput() {
        Account fromAccount = new Account(
                1L,
                10L,
                AccountType.CHECKING,
                new BigDecimal("1000.00"),
                AccountCurrency.USD,
                timeForTesting,
                timeForTesting
        );

        Account toAccount = new Account(
                2L,
                8L,
                AccountType.CHECKING,
                new BigDecimal("1500.00"),
                AccountCurrency.USD,
                timeForTesting,
                timeForTesting
        );

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(fromAccount));

        when(accountRepository.findById(2L))
                .thenReturn(Optional.of(toAccount));

        TransferRequest request = new TransferRequest(
                1L,
                2L,
                new BigDecimal("200.00"),
                AccountCurrency.USD,
                "Transfer"
        );

        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        TransactionEntryResponse debitResponse = new TransactionEntryResponse(
                1L,
                new BigDecimal("200.00"),
                AccountCurrency.USD,
                EntryType.DEBIT
        );

        TransactionEntryResponse creditResponse = new TransactionEntryResponse(
                2L,
                new BigDecimal("200.00"),
                AccountCurrency.USD,
                EntryType.CREDIT
        );

        TransactionResponse response = new TransactionResponse(
                1L,
                TransactionType.TRANSFER,
                "Transfer",
                timeForTesting,
                List.of(debitResponse, creditResponse)
        );

        when(transactionMapper.toDto(any(Transaction.class)))
                .thenReturn(response);

        TransactionResponse result = transactionService.transfer(request);

        assertEquals(new BigDecimal("800.00"), fromAccount.getAccountBalance());
        assertEquals(new BigDecimal("1700.00"), toAccount.getAccountBalance());

        verify(accountRepository).findById(1L);
        verify(accountRepository).findById(2L);
        verify(accountRepository).save(fromAccount);
        verify(accountRepository).save(toAccount);
        verify(transactionRepository).save(any(Transaction.class));
    }


    @Test
    void deposit_shouldThrowException_whenAccountNotFound() {
        when(accountRepository.findById(1L))
                .thenReturn(Optional.empty());

        DepositRequest request = new DepositRequest(
                1L,
                new BigDecimal("300.00"),
                AccountCurrency.USD,
                "Deposit"
        );

        assertThrows(AccountNotFoundException.class, () -> {
            transactionService.deposit(request);});

        verify(accountRepository, never()).save(any(Account.class));
        verify(transactionRepository,never()).save(any(Transaction.class));
    }


    @Test
    void withdraw_shouldThrowException_whenAccountNotFound() {
        when(accountRepository.findById(1L))
                .thenReturn(Optional.empty());

        WithdrawalRequest request = new WithdrawalRequest(
                1L,
                new BigDecimal("300.00"),
                AccountCurrency.USD,
                "Withdraw"
        );

        assertThrows(AccountNotFoundException.class, () -> {
            transactionService.withdraw(request);});

        verify(accountRepository, never()).save(any(Account.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }


    @Test
    void transfer_shouldThrowException_whenFromAccountNotFound() {
        when(accountRepository.findById(1L))
                .thenReturn(Optional.empty());

        TransferRequest request = new TransferRequest(
                1L,
                2L,
                new BigDecimal("300.00"),
                AccountCurrency.USD,
                "Transfer"
        );

        assertThrows(AccountNotFoundException.class, () -> {
            transactionService.transfer(request);});

        verify(accountRepository, never()).save(any(Account.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }


    @Test
    void transfer_shouldThrowException_whenToAccountNotFound() {
        Account fromAccount = new Account(
                1L,
                10L,
                AccountType.CHECKING,
                new BigDecimal("1000.00"),
                AccountCurrency.USD,
                timeForTesting,
                timeForTesting
        );

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(2L))
                .thenReturn(Optional.empty());

        TransferRequest request = new TransferRequest(
                1L,
                2L,
                new BigDecimal("300.00"),
                AccountCurrency.USD,
                "Transfer"
        );

        assertThrows(AccountNotFoundException.class, () -> {
            transactionService.transfer(request);});

        verify(accountRepository,never()).save(any(Account.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }


    @Test
    void deposit_shouldThrowException_whenAmountZeroOrNegative() {
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

        DepositRequest zeroAmountRequest = new DepositRequest(
                1L,
                new BigDecimal("0.00"),
                AccountCurrency.USD,
                "Deposit"
        );

        DepositRequest negativeAmountRequest = new DepositRequest(
                1L,
                new BigDecimal("-100.00"),
                AccountCurrency.USD,
                "Deposit"
        );

        assertThrows(InvalidDepositAmountException.class, () -> {
            transactionService.deposit(zeroAmountRequest);});

        assertThrows(InvalidDepositAmountException.class, () -> {
            transactionService.deposit(negativeAmountRequest);});

        verify(accountRepository, never()).save(any(Account.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }


    @Test
    void withdraw_shouldThrowException_whenAmountZeroOrNegative() {
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

        WithdrawalRequest zeroAmountRequest = new WithdrawalRequest(
                1L,
                new BigDecimal("0.00"),
                AccountCurrency.USD,
                "Withdraw"
        );

        WithdrawalRequest negativeAmountRequest = new WithdrawalRequest(
                1L,
                new BigDecimal("-200.00"),
                AccountCurrency.USD,
                "Withdraw"
        );

        assertThrows(InvalidWithdrawalAmountException.class, () -> {
            transactionService.withdraw(zeroAmountRequest);
        });
        assertThrows(InvalidWithdrawalAmountException.class, () -> {
            transactionService.withdraw(negativeAmountRequest);
        });

        verify(accountRepository, never()).save(any(Account.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }


    @Test
    void withdraw_shouldThrowException_whenInsufficientBalance() {
        Account account = new Account(
                1L,
                10L,
                AccountType.CHECKING,
                new BigDecimal("200.00"),
                AccountCurrency.USD,
                timeForTesting,
                timeForTesting
        );

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        WithdrawalRequest request = new WithdrawalRequest(
                1L,
                new BigDecimal("300.00"),
                AccountCurrency.USD,
                "Withdraw"
        );

        assertThrows(InsufficientBalanceException.class, () -> {
            transactionService.withdraw(request);
        });
        assertEquals(new BigDecimal("200.00"), account.getAccountBalance());

        verify(accountRepository, never()).save(any(Account.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }


    @Test
    void transfer_ShouldThrowException_whenTransferExceedsFromAccountBalance() {
        Account fromAccount = new Account(
                1L,
                10L,
                AccountType.CHECKING,
                new BigDecimal("1000.00"),
                AccountCurrency.USD,
                timeForTesting,
                timeForTesting
        );

        Account toAccount = new Account(
                2L,
                8L,
                AccountType.CHECKING,
                new BigDecimal("1500.00"),
                AccountCurrency.USD,
                timeForTesting,
                timeForTesting
        );

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(fromAccount));

        when(accountRepository.findById(2L))
                .thenReturn(Optional.of(toAccount));

        TransferRequest request = new TransferRequest(
                1L,
                2L,
                new BigDecimal("2000.00"),
                AccountCurrency.USD,
                "Transfer"
        );

        assertThrows(InsufficientBalanceException.class, () -> {
            transactionService.transfer(request);
        });

        verify(accountRepository, never()).save(any(Account.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

}
