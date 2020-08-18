package com.example.modular.mappers;

import com.example.modular.model.Balance;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface BalanceMapper {

    @Update("UPDATE BALANCE SET amount = #{amount} WHERE currency = #{currency} AND accountId = #{accountId}")
    void updateBalance(Long accountId, String currency, BigDecimal amount);

    @Select("SELECT * FROM BALANCE WHERE CURRENCY = #{currency} AND accountId = #{accountId}")
    Optional<Balance> getBalance(@Param("accountId") Long id, @Param("currency") String currency);

    @Options(useGeneratedKeys = true, keyProperty = "balanceId", keyColumn = "balanceId")
    @Insert("INSERT INTO BALANCE(accountId,currency,amount) values (#{accountId},#{currency},#{amount})")
    void saveBalance(Balance balance);
}
