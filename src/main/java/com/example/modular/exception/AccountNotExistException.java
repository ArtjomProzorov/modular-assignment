package com.example.modular.exception;

public class AccountNotExistException extends RuntimeException {

    public AccountNotExistException(Long accountId) {
        super("No account with ID " + accountId + " found");
    }
}
