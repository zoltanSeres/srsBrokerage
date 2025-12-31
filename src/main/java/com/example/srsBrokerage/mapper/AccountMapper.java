package com.example.srsBrokerage.mapper;

import com.example.srsBrokerage.dto.request.user.CreateAccountRequest;
import com.example.srsBrokerage.dto.response.user.AccountResponse;
import com.example.srsBrokerage.model.Account;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountMapper {
    public Account toEntity(CreateAccountRequest createAccountRequest) {
        Account account = new Account();

        account.setAccountType(createAccountRequest.accountType());
        account.setAccountBalance(createAccountRequest.accountBalance());
        account.setCurrency(createAccountRequest.accountCurrency());

        return account;
    }


    public AccountResponse toDto(Account account){

        return new AccountResponse(
                account.getId(),
                account.getUserId(),
                account.getAccountBalance(),
                account.getAccountType(),
                account.getAccountCurrency(),
                account.getCreatedAt(),
                account.getUpdatedAt()
        );
    }


    public List<AccountResponse>toDtoList(List<Account> accounts) {
        return accounts.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
