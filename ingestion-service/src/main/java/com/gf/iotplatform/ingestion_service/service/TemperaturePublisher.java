package com.gf.iotplatform.ingestion_service.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class TemperaturePublisher {

    private final RabbitTemplate rabbitTemplate;

    private static final String EXCHANGE = "temperature.received.event";
    private static final String ROUTING_KEY = "temperature.received.event";

    public TemperaturePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendTemperature(String message) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, message);
    }
}