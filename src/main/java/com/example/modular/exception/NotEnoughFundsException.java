package com.example.modular.exception;

public class NotEnoughFundsException extends RuntimeException {

    public NotEnoughFundsException(String currency) {
        super(currency + " balance dot not have enough funds");
    }
}
