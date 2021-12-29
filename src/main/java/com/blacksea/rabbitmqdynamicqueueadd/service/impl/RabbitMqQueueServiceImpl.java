package com.blacksea.rabbitmqdynamicqueueadd.service.impl;

import com.blacksea.rabbitmqdynamicqueueadd.configuration.model.RabbitMqConfigModel;
import com.blacksea.rabbitmqdynamicqueueadd.service.RabbitMqQueueService;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class RabbitMqQueueServiceImpl implements RabbitMqQueueService {
    private final RabbitAdmin rabbitAdmin;
    private final RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;
    private final RabbitMqConfigModel rabbitMqConfigModel;

    public RabbitMqQueueServiceImpl(RabbitAdmin rabbitAdmin, RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry, RabbitMqConfigModel rabbitMqConfigModel) {
        this.rabbitAdmin = rabbitAdmin;
        this.rabbitListenerEndpointRegistry = rabbitListenerEndpointRegistry;
        this.rabbitMqConfigModel = rabbitMqConfigModel;
    }

    @Override
    public void addNewQueue(String queueName) {
        Queue queue = new Queue(queueName, true, false, false);
        Binding binding = new Binding(
                queueName,
                Binding.DestinationType.QUEUE,
                rabbitMqConfigModel.getExchange(),
                queueName,
                null
        );
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareBinding(binding);
        this.addQueueToListener(rabbitMqConfigModel.getExchange(),queueName);
    }

    @Override
    public void removeQueue(String queueName) {
        log.info("removing queue : " + queueName + " from listener by exchange name : " + this.rabbitMqConfigModel.getExchange());
        if (checkQueueExistOnListener(this.rabbitMqConfigModel.getExchange(),queueName)) {
            this.getMessageListenerContainerById(this.rabbitMqConfigModel.getExchange()).removeQueueNames(queueName);
            log.info("deleting queue from rabbit management");
            this.rabbitAdmin.deleteQueue(queueName);
        } else {
            log.info("given queue name : " + queueName + " not exist on listener by exchange name : " + this.rabbitMqConfigModel.getExchange());
        }
    }

    private void addQueueToListener(String exchangeName,String queueName) {
        if (!checkQueueExistOnListener(exchangeName,queueName)) {
            this.getMessageListenerContainerById(exchangeName).addQueueNames(queueName);
            log.info("queue ");
        }
    }

    public Boolean checkQueueExistOnListener(String exchangeName, String queueName) {
        try {
            log.info("checking queueName : " + queueName + " exist on listener exchangeName : " + exchangeName);
            String[] queueNames = this.getMessageListenerContainerById(exchangeName).getQueueNames();
            if (queueNames.length > 0) {
                log.info("checking " + queueName + " exist on active queues");
                for (String name : queueNames) {
                    log.info("name : " + name + " with checking name : " + queueName);
                    if (name.equals(queueName)) {
                        log.info("queue name exist on listener, returning true");
                        return Boolean.TRUE;
                    }
                }
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            log.error("Error on checking queue exist on listener");
            return Boolean.FALSE;
        }
    }

    private AbstractMessageListenerContainer getMessageListenerContainerById(String exchangeName) {
        log.info("getting message listener container by exchange name : " + exchangeName);
        return ((AbstractMessageListenerContainer) this.rabbitListenerEndpointRegistry
                .getListenerContainer(exchangeName)
        );
    }
}
