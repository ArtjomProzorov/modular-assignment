package com.example.modular.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@Table("Account")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Account {

    @Id
    private Long accountId;

    @Column("country")
    private String country;

    @Column("customerId")
    private Long customerId;

    private List<Balance> balances;

}
