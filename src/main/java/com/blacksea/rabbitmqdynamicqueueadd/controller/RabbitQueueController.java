package com.blacksea.rabbitmqdynamicqueueadd.controller;

import com.blacksea.rabbitmqdynamicqueueadd.rabbit.RabbitMqSender;
import com.blacksea.rabbitmqdynamicqueueadd.service.RabbitMqQueueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rabbit-queue")
public class RabbitQueueController {
    private final RabbitMqQueueService rabbitMqQueueService;
    private final RabbitMqSender rabbitMqSender;

    public RabbitQueueController(RabbitMqQueueService rabbitMqQueueService, RabbitMqSender rabbitMqSender) {
        this.rabbitMqQueueService = rabbitMqQueueService;
        this.rabbitMqSender = rabbitMqSender;
    }

    @GetMapping("/add/{queueName}")
    public ResponseEntity<String> addQueue(@PathVariable String queueName) {
        this.rabbitMqQueueService.addNewQueue(queueName);
        return ResponseEntity.ok("Queue added with name : " + queueName);
    }

    @GetMapping("/delete/{queueName}")
    public ResponseEntity<String> removeQueue(@PathVariable String queueName) {
        this.rabbitMqQueueService.removeQueue(queueName);
        return ResponseEntity.ok("Queue removed with name : " + queueName);
    }

    @GetMapping("/send-message/{message}/{queueName}")
    public void sendMessage(@PathVariable String message,@PathVariable String queueName) {
        this.rabbitMqSender.sendToRabbitWithQueueName(queueName,message);
    }
}
