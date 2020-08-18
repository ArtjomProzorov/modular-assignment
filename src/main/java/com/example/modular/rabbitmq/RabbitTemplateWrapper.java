package com.example.modular.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class RabbitTemplateWrapper {

    private final RabbitTemplate rabbitTemplate;

    public void addFundsRabbitMqInfo(BigDecimal amount, String currency, Long accountId) {
        String message = amount + " " + currency + " was added to the account id = " + accountId;
        rabbitTemplate.convertAndSend(ConfigureRabbitMq.EXCHANGE_NAME, "bank.add", message);
    }

    public void decreaseBalanceRabbitMqInfo(BigDecimal amount, String currency, Long accountId) {
        String message = amount + " " + currency + " was withdrawn from account id = " + accountId;
        rabbitTemplate.convertAndSend(ConfigureRabbitMq.EXCHANGE_NAME, "bank.withdraw", message);
    }

}
