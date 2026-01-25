package com.example.srsBrokerage.mapper;

import com.example.srsBrokerage.dto.request.account.CreateAccountRequest;
import com.example.srsBrokerage.dto.response.account.AccountResponse;
import com.example.srsBrokerage.model.Account;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountMapper {
    public Account toEntity(CreateAccountRequest createAccountRequest) {
        Account account = new Account();

        account.setAccountType(createAccountRequest.accountType());
        account.setAccountCurrency(createAccountRequest.accountCurrency());

        return account;
    }


    public AccountResponse toDto(Account account){

        return new AccountResponse(
                account.getId(),
                account.getUserId(),
                account.getAccountType(),
                account.getAccountBalance(),
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
