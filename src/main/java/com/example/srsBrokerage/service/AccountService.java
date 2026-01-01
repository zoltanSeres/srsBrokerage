package com.example.srsBrokerage.service;

import com.example.srsBrokerage.dto.request.account.CreateAccountRequest;
import com.example.srsBrokerage.dto.response.account.AccountResponse;
import com.example.srsBrokerage.exceptions.*;
import com.example.srsBrokerage.mapper.AccountMapper;
import com.example.srsBrokerage.model.Account;
import com.example.srsBrokerage.model.User;
import com.example.srsBrokerage.repository.AccountRepository;
import com.example.srsBrokerage.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private AccountMapper accountMapper;
    private UserRepository userRepository;

    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.userRepository = userRepository;
    }


    public AccountResponse createAccount(CreateAccountRequest createAccountRequest) {

        User user = userRepository.findById(createAccountRequest.userId())
                .orElseThrow(() -> new UserNotFoundException("User not found."));

        boolean exists = accountRepository.existsByUserIdAndAccountType(
                createAccountRequest.userId(),
                createAccountRequest.accountType()
        );
        if (exists) {
            throw new AccountTypeAlreadyExistsException("Only one account type allowed.");
        }

        if (createAccountRequest.accountBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidDepositAmountException("Deposit amount cannot be negative.");
        }

        Account account = accountMapper.toEntity(createAccountRequest);
        Account savedAccount = accountRepository.save(account);

        return accountMapper.toDto(savedAccount);
    }


    public AccountResponse findAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account with ID " + id + " does not exist."));

        return accountMapper.toDto(account);
    }


    public List<AccountResponse> findAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(accountMapper::toDto)
                .toList();
    }


    public void deleteAccount(Long id) {
        Account accountToDelete = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account with ID " + id + " does not exist."));

        if (accountToDelete.getAccountBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new AccountCannotBeClosedException("Account cannot be closed. Balance must be " +
                    accountToDelete.getAccountCurrency() + " 0.00. " +
                    "Your current balance is " + accountToDelete.getAccountBalance() + ".");
        }

        accountRepository.delete(accountToDelete);
    }
}
