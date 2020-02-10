package com.itheima.web.controller.rabbitMQ;

public interface RabbitMQProducer {
    public void sendData(String routingKey , Object data);
}
