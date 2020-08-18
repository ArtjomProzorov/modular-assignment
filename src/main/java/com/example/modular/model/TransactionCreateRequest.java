package com.example.modular.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransactionCreateRequest {

    private Long receiverId;
    private Long accountId;
    private BigDecimal amount;
    private String currency;
    private String direction;
    private String description;
}
