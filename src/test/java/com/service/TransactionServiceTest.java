package com.service;

import com.example.modular.config.PersistenceConfig;
import com.example.modular.enums.Currency;
import com.example.modular.enums.Direction;
import com.example.modular.exception.NotEnoughFundsException;
import com.example.modular.mappers.AccountMapper;
import com.example.modular.mappers.BalanceMapper;
import com.example.modular.mappers.TransactionMapper;
import com.example.modular.model.Account;
import com.example.modular.model.Balance;
import com.example.modular.model.Transaction;
import com.example.modular.model.TransactionCreateRequest;
import com.example.modular.model.TransactionDto;
import com.example.modular.rabbitmq.RabbitTemplateWrapper;
import com.example.modular.service.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = {PersistenceConfig.class, TransactionService.class,
        TransactionMapper.class, AccountMapper.class, BalanceMapper.class, AccountMapper.class})
public class TransactionServiceTest {


    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private BalanceMapper balanceMapper;

    @MockBean
    RabbitTemplateWrapper rabbitTemplateWrapper;

    @Test
    public void shouldReturnListOfTransactions() {
        TransactionCreateRequest inRequest = inTransactionRequest();
        TransactionCreateRequest outRequest = outTransactionRequest();
        transactionService.createTransaction(inRequest);
        transactionService.createTransaction(outRequest);
        Assertions.assertEquals(transactionMapper.getTransactions(1L).size(), 2);
    }

    @Test
    public void shouldThrowExceptionIfBalanceIsLowAndDirectionIsOut() {
        TransactionCreateRequest transactionCreateRequest = outTransactionRequest();
        Assertions.assertThrows(NotEnoughFundsException.class, () -> transactionService.createTransaction(transactionCreateRequest));
    }


    @Test
    public void shouldCreateTransaction() {
        TransactionDto transactionDto = transactionService.createTransaction(inTransactionRequest());
        Transaction transaction = transactionMapper.getTransactions(1L).get(0);
        Assertions.assertEquals(transaction.getAccountId(), transactionDto.getAccountId());
        Assertions.assertEquals(transaction.getAmount(), transactionDto.getAmount());
        Assertions.assertEquals(transaction.getCurrency(), transactionDto.getCurrency());
        Assertions.assertEquals(transaction.getDescription(), transactionDto.getDescription());
        Assertions.assertEquals(transaction.getDirection(), transactionDto.getDirection());
    }

    private TransactionCreateRequest inTransactionRequest() {
        return TransactionCreateRequest.builder()
                .accountId(1L)
                .amount(BigDecimal.valueOf(1000))
                .currency(Currency.EUR.name())
                .description("Family support")
                .direction(Direction.IN.name())
                .build();
    }

    private TransactionCreateRequest outTransactionRequest() {
        return TransactionCreateRequest.builder()
                .accountId(1L)
                .receiverId(2L)
                .amount(BigDecimal.valueOf(1000))
                .currency(Currency.EUR.name())
                .description("Birthday money")
                .direction(Direction.OUT.name())
                .build();
    }

    @BeforeEach
    public void saveAccounts() {
        Account account = Account.builder()
                .customerId(1L)
                .country("Estonia")
                .build();

        Account account2 = Account.builder()
                .customerId(2L)
                .country("Russia")
                .build();

        accountMapper.saveAccount(account);
        accountMapper.saveAccount(account2);

        List<Balance> balances = Stream.of("EUR", "GBP")
                .map(currency -> Balance.builder()
                        .currency(currency)
                        .amount(BigDecimal.ZERO)
                        .build())
                .map(b -> b.setAccountId(account.getAccountId()))
                .collect(toList());
        balances.forEach(balanceMapper::saveBalance);

        List<Balance> balances2 = Stream.of("EUR", "GBP")
                .map(currency -> Balance.builder()
                        .currency(currency)
                        .amount(BigDecimal.ZERO)
                        .build())
                .map(b -> b.setAccountId(account2.getAccountId()))
                .collect(toList());
        balances2.forEach(balanceMapper::saveBalance);

    }
}
