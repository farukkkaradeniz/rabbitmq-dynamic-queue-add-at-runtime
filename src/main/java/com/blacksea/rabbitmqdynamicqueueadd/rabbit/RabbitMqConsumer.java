package com.blacksea.rabbitmqdynamicqueueadd.rabbit;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class RabbitMqConsumer {
    @RabbitListener(id = "${props.rabbitmq.exchange}",queues = {"${props.rabbitmq.queue}"},concurrency = "2")
    public void receiver(String message) {
        log.info("message received, message : {}",message);
    }
}
