package com.blacksea.rabbitmqdynamicqueueadd.rabbit;

import com.blacksea.rabbitmqdynamicqueueadd.configuration.model.RabbitMqConfigModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class RabbitMqSender {
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMqConfigModel rabbitMqConfigModel;

    public RabbitMqSender(RabbitTemplate rabbitTemplate, RabbitMqConfigModel rabbitMqConfigModel) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMqConfigModel = rabbitMqConfigModel;
    }

    public void sendToRabbitWithQueueName(String queueName,String message) {
        log.info("sending message to queue with name : {} and message : {}",queueName,message);
        this.rabbitTemplate.convertAndSend(this.rabbitMqConfigModel.getExchange(),queueName,message);
        log.info("message sent to rabbit queue");
    }
}
