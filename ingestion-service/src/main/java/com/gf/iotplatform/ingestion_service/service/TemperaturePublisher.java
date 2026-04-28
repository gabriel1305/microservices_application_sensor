package com.gf.iotplatform.ingestion_service.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.gf.iotplatform.ingestion_service.model.SensorData;

@Service
public class TemperaturePublisher {

    private final RabbitTemplate rabbitTemplate;

    private static final String EXCHANGE = "temperature.received.event";
    private static final String ROUTING_KEY = "temperature-received";

    public TemperaturePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendTemperature(SensorData data) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, data);
    }
}