package com.itheima.web.controller.rabbitMQ.impl;


import com.itheima.web.controller.rabbitMQ.RabbitMQProducer;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducerImpl implements RabbitMQProducer {
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Override
    public void sendData(String routingKey, Object data) {
        amqpTemplate.convertAndSend(routingKey , data);
    }
}
