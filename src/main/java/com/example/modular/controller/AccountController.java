package com.example.modular.controller;

import com.example.modular.model.Account;
import com.example.modular.model.AccountCreateRequest;
import com.example.modular.model.AccountDto;
import com.example.modular.model.Balance;
import com.example.modular.model.Transaction;
import com.example.modular.model.TransactionCreateRequest;
import com.example.modular.model.TransactionDto;
import com.example.modular.service.AccountService;
import com.example.modular.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final TransactionService transactionService;

    @PostMapping("/create")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountCreateRequest createRequest) {
        AccountDto account = accountService.create(createRequest);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/get/accounts")
    public ResponseEntity<List<AccountDto>> getAccounts() {
        List<AccountDto> accounts = accountService.getAccounts();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/get/{accountId}")
    public ResponseEntity<Account> getAccount(@PathVariable Long accountId) {
        Account account = accountService.searchById(accountId);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/get/{accountId}/balances")
    public ResponseEntity<List<Balance>> getBalances(@PathVariable Long accountId) {
        return ResponseEntity.ok(accountService.getBalances(accountId));
    }

    @PostMapping("/create/transaction")
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionCreateRequest request) {
        return ResponseEntity.ok(transactionService.createTransaction(request));
    }

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<TransactionDto>> getTransactions(@PathVariable Long accountId) {
        List<TransactionDto> transactions = transactionService.getTransactions(accountId);
        return ResponseEntity.ok(transactions);
    }
}
