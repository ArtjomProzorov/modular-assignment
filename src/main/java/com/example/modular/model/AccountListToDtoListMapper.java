package com.example.modular.model;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AccountListToDtoListMapper implements Function<List<Account>, List<AccountDto>> {
    @Override
    public List<AccountDto> apply(List<Account> accounts) {
        return accounts.stream().map(a -> AccountDto.builder()
                .accountId(a.getAccountId())
                .customerId(a.getCustomerId())
                .country(a.getCountry())
                .balances(a.getBalances())
                .build())
                .collect(Collectors.toList());
    }
}
