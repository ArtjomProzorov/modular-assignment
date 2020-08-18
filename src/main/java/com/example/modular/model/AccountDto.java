package com.example.modular.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AccountDto {

    private Long accountId;
    private String country;
    private Long customerId;
    private List<Balance> balances;

}
