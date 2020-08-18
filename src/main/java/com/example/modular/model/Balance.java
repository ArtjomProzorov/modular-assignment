package com.example.modular.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("Balance")
@Accessors(chain = true)
public class Balance {

    @Id
    private Long balanceId;

    @Column("account_id")
    private Long accountId;

    @Column("currency")
    private String currency;

    @Column("amount")
    private BigDecimal amount;
}
