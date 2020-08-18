package com.example.modular.model;

import java.util.List;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreateRequest {

    private Long customerId;
    private String country;
    private List<String> currencies;

}
