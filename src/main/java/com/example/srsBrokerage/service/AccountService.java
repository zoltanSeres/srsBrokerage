package com.example.srsBrokerage.service;


import com.example.srsBrokerage.dto.request.user.CreateAccountRequest;
import com.example.srsBrokerage.dto.response.user.AccountResponse;
import com.example.srsBrokerage.exceptions.NegativeAccountBalanceException;
import com.example.srsBrokerage.mapper.AccountMapper;
import com.example.srsBrokerage.model.Account;
import com.example.srsBrokerage.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private AccountMapper accountMapper;

    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }


    public AccountResponse createAccount(CreateAccountRequest createAccountRequest) {
        if (!createAccountRequest.accountType().matches("[A-Za-z]+")) {
            throw new IllegalArgumentException("Invalid account type.");
        }

        if (createAccountRequest.accountBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeAccountBalanceException("Balance cannot be negative.");
        }

        //de currency enums for accountCurrency instead of String.

        Account account = accountMapper.toEntity(createAccountRequest);
        Account savedAccount = accountRepository.save(account);

        return accountMapper.toDto(savedAccount);
    }
}
