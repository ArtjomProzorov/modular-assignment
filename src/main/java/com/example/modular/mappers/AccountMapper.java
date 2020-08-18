package com.example.modular.mappers;

import com.example.modular.model.Account;
import com.example.modular.model.Balance;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountMapper {

    @Select("SELECT * FROM ACCOUNT WHERE accountId = #{accountId}")
    @Results(value = {@Result(property = "accountId", column = "accountId"), @Result(property = "customerId", column = "customerId"),
            @Result(property = "balances", javaType = List.class, column = "accountId", many = @Many(select = "getAllBalances"))
    })
    Optional<Account> getAccount(@Param("accountId") Long id);


    @Select("SELECT * FROM BALANCE WHERE accountId = #{accountId}")
    List<Balance> getAllBalances(Long accountId);


    @Results({
            @Result(property = "accountId", column = "accountId"),
            @Result(property = "customerId", column = "customerId"),
            @Result(property = "balances", javaType = List.class, column = "accountId", many = @Many(select = "getAllBalances"))
    })
    @Select("SELECT * FROM ACCOUNT")
    List<Account> findAll();

    @Select("SELECT * FROM ACCOUNT WHERE customerId = #{customerId}")
    Optional<Account> findByCustomerId(@Param("customerId") Long id);

    @Select("SELECT * FROM ACCOUNT WHERE accountId = #{accountId}")
    Optional<Account> findByAccountId(@Param("accountId") Long id);

    @Options(useGeneratedKeys = true, keyProperty = "accountId", keyColumn = "accountId")
    @Insert("INSERT INTO ACCOUNT(customerId,country) values (#{customerId},#{country})")
    void saveAccount(Account account);

    @Select("Select amount from balance where accountId=#{accountId} AND currency=#{currency}")
    Optional<BigDecimal> getCurrency(@Param("accountId") Long id, @Param("currency") String currency, @Param("amount") BigDecimal amount);
}
