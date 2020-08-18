package com.example.modular.exception;

public class AccountAlreadyExistsException extends RuntimeException {

    public AccountAlreadyExistsException() {
        super("Account already exists");
    }
}
