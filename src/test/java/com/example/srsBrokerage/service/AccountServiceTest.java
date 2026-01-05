package com.example.srsBrokerage.service;


import com.example.srsBrokerage.dto.request.account.CreateAccountRequest;
import com.example.srsBrokerage.dto.response.account.AccountResponse;
import com.example.srsBrokerage.enums.AccountType;
import com.example.srsBrokerage.enums.AccountCurrency;
import com.example.srsBrokerage.mapper.AccountMapper;
import com.example.srsBrokerage.model.Account;
import com.example.srsBrokerage.model.User;
import com.example.srsBrokerage.repository.AccountRepository;
import com.example.srsBrokerage.repository.UserRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountService accountService;

    LocalDateTime timeForTesting = LocalDateTime.now();
    //used only for testing

    @Test
    void createAccount_shouldReturnAccount_whenValidInput() {
        CreateAccountRequest createAccountRequest = new CreateAccountRequest(
                1L,
                AccountType.CHECKING,
                new BigDecimal("1000.00"),
                AccountCurrency.USD
        );

        User testUser = new User("John", "Doe", "john@gmail.com", "123456789");

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(testUser));

        when(accountMapper.toEntity(any(CreateAccountRequest.class)))
                .thenAnswer(invocationOnMock -> {
                    CreateAccountRequest request = invocationOnMock.getArgument(0);
                    return new Account(
                            null,
                            null,
                            request.accountType(),
                            request.accountBalance(),
                            request.accountCurrency(),
                            timeForTesting,
                            timeForTesting
                    );
                });

        when(accountRepository.save(any(Account.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        when(accountMapper.toDto(any(Account.class)))
                .thenAnswer(invocationOnMock -> {
                    Account account = invocationOnMock.getArgument(0);
                    return new AccountResponse(
                            null,
                            null,
                            account.getAccountType(),
                            account.getAccountBalance(),
                            account.getAccountCurrency(),
                            timeForTesting,
                            timeForTesting
                    );
                });

        AccountResponse result = accountService.createAccount(createAccountRequest);

        assertNotNull(result);
        assertEquals(AccountType.CHECKING, result.accountType());
        assertEquals(createAccountRequest.accountBalance(), result.accountBalance());
        assertEquals(AccountCurrency.USD, result.accountCurrency());

        verify(userRepository).findById(1L);
        verify(accountRepository).save(any(Account.class));
    }


    @Test
    void findAccountById_ShouldReturnAccount_whenValidInput() {
        Account account = new Account(
                1L,
                2L,
                AccountType.CHECKING,
                new BigDecimal("1000.00"),
                AccountCurrency.USD,
                timeForTesting,
                timeForTesting
        );

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        when(accountMapper.toDto(account)).thenReturn(new AccountResponse(
                        1L,
                        2L,
                        AccountType.CHECKING,
                        new BigDecimal("1000.00"),
                        AccountCurrency.USD,
                        timeForTesting,
                        timeForTesting)
                );

        AccountResponse result = accountService.findAccountById(1L);

        assertNotNull(result);
        assertEquals(AccountType.CHECKING, result.accountType());
        assertEquals(new BigDecimal("1000.00"), result.accountBalance());
        assertEquals(AccountCurrency.USD, result.accountCurrency());

        verify(accountRepository).findById(1L);
        verify(accountMapper).toDto(account);
    }


    @Test
    void findAllAccounts_shouldReturnAccounts_whenInputValid() {
        List<Account> accountList = new ArrayList<>();

        Account accountOne = new Account(
                1L,
                2L,
                AccountType.CHECKING,
                new BigDecimal("1000.00"),
                AccountCurrency.USD,
                timeForTesting,
                timeForTesting
        );

        Account accountTwo = new Account(
                2L,
                3L,
                AccountType.CHECKING,
                new BigDecimal("1500.00"),
                AccountCurrency.USD,
                timeForTesting,
                timeForTesting
        );

        Account accountThree = new Account(
                7L,
                9L,
                AccountType.CHECKING,
                new BigDecimal("800.00"),
                AccountCurrency.USD,
                timeForTesting,
                timeForTesting
        );

        accountList.add(accountOne);
        accountList.add(accountTwo);
        accountList.add(accountThree);

        when(accountRepository.findAll()).thenReturn(accountList);

        when(accountMapper.toDto(accountOne)).thenReturn(new AccountResponse(
                1L,
                2L,
                AccountType.CHECKING,
                new BigDecimal("1000.00"),
                AccountCurrency.USD,
                timeForTesting,
                timeForTesting
        ));

        when(accountMapper.toDto(accountTwo)).thenReturn(new AccountResponse(
                2L,
                3L,
                AccountType.CHECKING,
                new BigDecimal("1500.00"),
                AccountCurrency.USD,
                timeForTesting,
                timeForTesting
        ));

        when(accountMapper.toDto(accountThree)).thenReturn(new AccountResponse(
                7L,
                9L,
                AccountType.CHECKING,
                new BigDecimal("800.00"),
                AccountCurrency.USD,
                timeForTesting,
                timeForTesting
        ));

        List<AccountResponse>accountResponses = accountService.findAllAccounts();

        assertEquals(3, accountResponses.size());
        assertEquals(new BigDecimal("800.00"), accountResponses.get(2).accountBalance());

        verify(accountRepository).findAll();
        verify(accountMapper, times(3)).toDto(any(Account.class));
    }
}
