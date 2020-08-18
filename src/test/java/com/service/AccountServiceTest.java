package com.service;

import com.example.modular.config.PersistenceConfig;
import com.example.modular.mappers.AccountMapper;
import com.example.modular.model.Account;
import com.example.modular.model.AccountCreateRequest;
import com.example.modular.service.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = {PersistenceConfig.class, AccountService.class, AccountMapper.class})
public class AccountServiceTest {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AccountService accountService;


    @Test
    public void shouldCreateAccount() {
        Assertions.assertEquals(0, accountMapper.findAll().size());
        Account account = getAccount();
        accountMapper.saveAccount(account);
        Assertions.assertEquals(1, accountMapper.findAll().size());
        Assertions.assertTrue(accountMapper.findByCustomerId(account.getCustomerId()).isPresent());
    }

    @Test
    public void shouldThrowExceptionIfAccountExists() {
        Account account = getAccount();
        accountMapper.saveAccount(account);
        Assertions.assertThrows(RuntimeException.class, () -> accountService.create(AccountCreateRequest.builder()
                .country("Estonia")
                .customerId(1L)
                .build()));
    }

    @Test
    public void shouldReturnAccountById() {
        accountMapper.saveAccount(getAccount());
        Optional<Account> account = accountMapper.findByCustomerId(1L);
        Assertions.assertTrue(account.isPresent());
    }

    private Account getAccount() {
        return Account.builder()
                .customerId(1L)
                .country("Estonia")
                .build();
    }
}
