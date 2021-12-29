package com.blacksea.rabbitmqdynamicqueueadd.service;

public interface RabbitMqQueueService {
    void addNewQueue(String queueName);
    void removeQueue(String queueName);
}
