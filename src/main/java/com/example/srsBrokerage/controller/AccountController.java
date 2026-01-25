package com.example.srsBrokerage.controller;

import com.example.srsBrokerage.dto.request.account.CreateAccountRequest;
import com.example.srsBrokerage.dto.response.account.AccountResponse;
import com.example.srsBrokerage.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(
            @Valid @RequestBody CreateAccountRequest createAccountRequest,
            Authentication authentication
    ) {
        AccountResponse accountResponse = accountService.createAccount(createAccountRequest, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> findAccountById(
            @PathVariable Long id,
            Authentication authentication
    ) {
        AccountResponse accountResponse = accountService.findAccountById(id, authentication);
        return ResponseEntity.ok(accountResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAccount(
            @PathVariable Long id,
            Authentication authentication
    ) {
        accountService.deleteAccount(id, authentication);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<AccountResponse>> findAllAccounts() {
        List<AccountResponse> accountResponses = accountService.findAllAccounts();
        return ResponseEntity.ok(accountResponses);
    }

    //add list all accounts by users too.
}
