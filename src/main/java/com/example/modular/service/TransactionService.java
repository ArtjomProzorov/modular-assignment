package com.example.modular.service;

import com.example.modular.exception.AccountNotExistException;
import com.example.modular.exception.BalanceNotFoundException;
import com.example.modular.exception.NotEnoughFundsException;
import com.example.modular.mappers.AccountMapper;
import com.example.modular.mappers.BalanceMapper;
import com.example.modular.mappers.TransactionMapper;
import com.example.modular.model.Account;
import com.example.modular.model.Balance;
import com.example.modular.model.Transaction;
import com.example.modular.model.TransactionCreateRequest;
import com.example.modular.model.TransactionDto;
import com.example.modular.model.TransactionListToDtoListMapper;
import com.example.modular.model.TransactionToDtoMapper;
import com.example.modular.rabbitmq.RabbitTemplateWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.example.modular.enums.Direction.IN;
import static com.example.modular.enums.Direction.OUT;
import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountMapper accountMapper;
    private final TransactionMapper transactionMapper;
    private final BalanceMapper balanceMapper;
    private final RabbitTemplateWrapper rabbitTemplateWrapper;

    @Transactional
    public TransactionDto createTransaction(TransactionCreateRequest request) {
        Account account = accountMapper.getAccount(request.getAccountId())
                .orElseThrow(() -> new AccountNotExistException(request.getAccountId()));
        Transaction transaction = proceedBasedOnDirection(account, request);
        transactionMapper.saveTransaction(transaction);
        createIncomingTransactionForTheRecipient(request, transaction);

        return new TransactionToDtoMapper().apply(transaction);
    }

    private void createIncomingTransactionForTheRecipient(TransactionCreateRequest request, Transaction transaction) {
        if (OUT.name().equals(request.getDirection())) {
            transactionMapper.saveTransaction(transaction.setDirection(IN.name())
                    .setAccountId(request.getReceiverId()));
        }
    }

    private Balance addFunds(Account account, String currency, BigDecimal amount) {
        Balance foundBalance = balanceMapper.getBalance(account.getAccountId(), currency)
                .orElseThrow(BalanceNotFoundException::new);
        BigDecimal fundsAfterAdding = foundBalance.getAmount().add(amount);
        balanceMapper.updateBalance(account.getAccountId(), currency, fundsAfterAdding);

        rabbitTemplateWrapper.addFundsRabbitMqInfo(amount, currency, account.getAccountId());

        return balanceMapper.getBalance(account.getAccountId(), currency)
                .orElseThrow(BalanceNotFoundException::new);
    }

    public List<TransactionDto> getTransactions(Long accountId) {
        List<Transaction> transactions = transactionMapper.getTransactions(accountId);
        if (transactions.isEmpty()) {
            return emptyList();
        } else {
            return new TransactionListToDtoListMapper().apply(transactions);
        }
    }

    private Balance decreaseBalance(Account account, String currency, BigDecimal amount) {
        BigDecimal fundsAfterSubtracting;
        Balance foundBalance = balanceMapper.getBalance(account.getAccountId(), currency)
                .orElseThrow(BalanceNotFoundException::new);
        if (ifBalanceExistsAndEnoughFunds(account.getAccountId(), currency, amount)) {
            fundsAfterSubtracting = foundBalance.getAmount().subtract(amount);
            balanceMapper.updateBalance(account.getAccountId(), currency, fundsAfterSubtracting);
        } else {
            throw new NotEnoughFundsException(currency);
        }
        rabbitTemplateWrapper.decreaseBalanceRabbitMqInfo(amount, currency, account.getAccountId());
        return foundBalance.setAmount(fundsAfterSubtracting);
    }

    private boolean ifBalanceExistsAndEnoughFunds(Long accountId, String currency, BigDecimal amount) {
        Optional<BigDecimal> currencyBalance = accountMapper.getCurrency(accountId, currency, amount);
        return currencyBalance.isPresent() && currencyBalance.get().compareTo(amount) >= 0;
    }

    private Transaction proceedBasedOnDirection(Account account, TransactionCreateRequest request) {
        Balance balance = new Balance();
        if (IN.name().equalsIgnoreCase(request.getDirection())) {
            balance = addFunds(account, request.getCurrency(), request.getAmount());
        }
        if (OUT.name().equalsIgnoreCase(request.getDirection())) {
            balance = decreaseBalance(account, request.getCurrency(), request.getAmount());
            addFundsToRecipientsAccountId(request.getCurrency(), request.getReceiverId(), request.getAmount());
        }

        return Transaction.builder()
                .accountId(request.getAccountId())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .description(request.getDescription())
                .direction(request.getDirection())
                .remainingBalance(balance.getAmount())
                .build();
    }

    private void addFundsToRecipientsAccountId(String currency, Long receiverId, BigDecimal amount) {
        Optional<Account> receiver = accountMapper.findByAccountId(receiverId);
        receiver.orElseThrow(() -> new AccountNotExistException(receiverId));

        Optional<Balance> balance = balanceMapper.getBalance(receiverId, currency);
        if (balance.isPresent()) {
            BigDecimal fundsAfterAdding = balance.get().getAmount().add(amount);
            balanceMapper.updateBalance(receiverId, currency, fundsAfterAdding);
        } else {
            throw new BalanceNotFoundException();
        }
    }

}
