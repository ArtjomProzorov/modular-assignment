package com.example.modular.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class TransactionDto {

    private Long accountId;
    private Long transactionId;
    private BigDecimal amount;
    private String currency;
    private String direction;
    private String description;
    private BigDecimal remainingBalance;

}
