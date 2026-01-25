package com.example.srsBrokerage.service;

import com.example.srsBrokerage.config.UserDetailsAdapter;
import com.example.srsBrokerage.dto.request.account.CreateAccountRequest;
import com.example.srsBrokerage.dto.response.account.AccountResponse;
import com.example.srsBrokerage.exceptions.*;
import com.example.srsBrokerage.mapper.AccountMapper;
import com.example.srsBrokerage.model.Account;
import com.example.srsBrokerage.model.User;
import com.example.srsBrokerage.repository.AccountRepository;
import com.example.srsBrokerage.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private AccountMapper accountMapper;
    private UserRepository userRepository;

    public AccountService(
            AccountRepository accountRepository,
            AccountMapper accountMapper,
            UserRepository userRepository
    ) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.userRepository = userRepository;
    }


    @Transactional
    public AccountResponse createAccount(
            CreateAccountRequest createAccountRequest,
            Authentication authentication
    ) {

        UserDetailsAdapter userDetailsAdapter = (UserDetailsAdapter) authentication.getPrincipal();
        Long loggedUserId = userDetailsAdapter.getUserId();

        User user = userRepository.findById(loggedUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found."));

        boolean exists = accountRepository.existsByUserIdAndAccountType(
                loggedUserId,
                createAccountRequest.accountType()
        );
        if (exists) {
            throw new AccountTypeAlreadyExistsException("Only one account type allowed.");
        }

        Account account = accountMapper.toEntity(createAccountRequest);

        account.setUserId(loggedUserId);
        account.setAccountBalance(BigDecimal.ZERO);

        Account savedAccount = accountRepository.save(account);

        return accountMapper.toDto(savedAccount);
    }


    public AccountResponse findAccountById(Long id, Authentication authentication) {

        UserDetailsAdapter userDetailsAdapter = (UserDetailsAdapter) authentication.getPrincipal();

        Long loggedUserId = userDetailsAdapter.getUserId();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !loggedUserId.equals(id)) {
            throw new AccessForbiddenException("You cannot perform this search.");
        }

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


    @Transactional
    public void deleteAccount(Long id, Authentication authentication) {

        UserDetailsAdapter userDetailsAdapter = (UserDetailsAdapter) authentication.getPrincipal();

        Long loggedUserId = userDetailsAdapter.getUserId();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !loggedUserId.equals(id)) {
            throw new AccessForbiddenException("You cannot perform this search.");
        }

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
