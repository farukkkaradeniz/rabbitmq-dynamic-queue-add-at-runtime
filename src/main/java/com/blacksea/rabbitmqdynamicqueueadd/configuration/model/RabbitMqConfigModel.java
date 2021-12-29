package com.blacksea.rabbitmqdynamicqueueadd.configuration.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
@Configuration
public class RabbitMqConfigModel {
    @Value("${props.rabbitmq.exchange}")
    private String exchange;
    @Value("${props.rabbitmq.queue}")
    private String queue;
    @Value("${props.rabbitmq.routing-key}")
    private String routingKey;
}
