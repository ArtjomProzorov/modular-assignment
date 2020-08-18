package com.example.modular.mappers;

import com.example.modular.model.Transaction;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionMapper {

    @Options(useGeneratedKeys = true, keyProperty = "transactionId", keyColumn = "transactionId")
    @Insert("INSERT INTO TRANSACTION(accountId,amount,currency,direction,description,remainingBalance) values (#{accountId},#{amount},#{currency},#{direction},#{description},#{remainingBalance})")
    void saveTransaction(Transaction transaction);

    @Select("SELECT * FROM TRANSACTION WHERE accountId = #{accountId}")
    List<Transaction> getTransactions(Long accountId);
}
