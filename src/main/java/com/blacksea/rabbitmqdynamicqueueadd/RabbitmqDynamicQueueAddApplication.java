package com.blacksea.rabbitmqdynamicqueueadd;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class RabbitmqDynamicQueueAddApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqDynamicQueueAddApplication.class, args);
    }

}
