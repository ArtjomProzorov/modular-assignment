package com.example.modular.service;

import com.example.modular.exception.AccountAlreadyExistsException;
import com.example.modular.exception.AccountNotExistException;
import com.example.modular.mappers.AccountMapper;
import com.example.modular.mappers.BalanceMapper;
import com.example.modular.model.Account;
import com.example.modular.model.AccountCreateRequest;
import com.example.modular.model.AccountDto;
import com.example.modular.model.AccountListToDtoListMapper;
import com.example.modular.model.Balance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountMapper accountMapper;
    private final BalanceMapper balanceMapper;

    public AccountDto create(AccountCreateRequest request) {
        Optional<Account> foundAccount = accountMapper.findByCustomerId(request.getCustomerId());
        if (foundAccount.isEmpty()) {
            Account account = Account.builder()
                    .customerId(request.getCustomerId())
                    .country(request.getCountry())
                    .build();

            accountMapper.saveAccount(account);
            List<Balance> balances = request.getCurrencies().stream()
                    .map(currency -> Balance.builder()
                            .currency(currency)
                            .amount(BigDecimal.ZERO)
                            .build())
                    .map(b -> b.setAccountId(account.getAccountId()))
                    .collect(toList());

            balances.forEach(balanceMapper::saveBalance);
            Account savedAccount = accountMapper.getAccount(account.getAccountId())
                    .orElseThrow(() -> new AccountNotExistException(account.getCustomerId()));
            return AccountDto.builder()
                    .accountId(savedAccount.getAccountId())
                    .country(savedAccount.getCountry())
                    .customerId(savedAccount.getCustomerId())
                    .balances(balances).build();
        } else {
            throw new AccountAlreadyExistsException();
        }
    }

    public Account searchById(Long id) {
        Optional<Account> account = accountMapper.getAccount(id);
        if (account.isPresent()) {
            return account.get();
        } else {
            throw new AccountAlreadyExistsException();
        }
    }

    public List<AccountDto> getAccounts() {
        List<Account> accounts = accountMapper.findAll();
        if (accounts.isEmpty()) {
            return emptyList();
        } else {
            return new AccountListToDtoListMapper().apply(accounts);
        }
    }

    public List<Balance> getBalances(Long accountId) {
        return accountMapper.getAllBalances(accountId);
    }
}
