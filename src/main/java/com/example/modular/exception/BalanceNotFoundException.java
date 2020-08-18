package com.example.modular.exception;

public class BalanceNotFoundException extends RuntimeException {

    public BalanceNotFoundException() {
        super("Balance not found");
    }
}


