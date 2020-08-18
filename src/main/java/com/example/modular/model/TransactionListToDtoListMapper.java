package com.example.modular.model;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TransactionListToDtoListMapper implements Function<List<Transaction>, List<TransactionDto>> {

    @Override
    public List<TransactionDto> apply(List<Transaction> transactions) {
        return transactions.stream().map(t -> TransactionDto.builder()
                .accountId(t.getAccountId())
                .amount(t.getAmount())
                .currency(t.getCurrency())
                .description(t.getDescription())
                .direction(t.getDirection())
                .transactionId(t.getTransactionId())
                .remainingBalance(t.getRemainingBalance()).build()).collect(Collectors.toList());
    }
}
