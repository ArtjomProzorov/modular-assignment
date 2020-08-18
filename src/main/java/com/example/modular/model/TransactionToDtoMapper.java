package com.example.modular.model;

import java.util.function.Function;

public class TransactionToDtoMapper implements Function<Transaction, TransactionDto> {

    @Override
    public TransactionDto apply(Transaction transaction) {
        return TransactionDto.builder()
                .transactionId(transaction.getTransactionId())
                .accountId(transaction.getAccountId())
                .amount(transaction.getAmount())
                .currency(transaction.getCurrency())
                .description(transaction.getDescription())
                .direction(transaction.getDirection())
                .remainingBalance(transaction.getRemainingBalance())
                .build();
    }
}
