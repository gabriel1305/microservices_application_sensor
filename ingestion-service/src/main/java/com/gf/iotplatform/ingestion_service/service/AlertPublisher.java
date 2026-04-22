package com.gf.iotplatform.ingestion_service.service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class AlertPublisher {

    private final RabbitTemplate rabbitTemplate;

    public AlertPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendAlert(String message) {
        rabbitTemplate.convertAndSend("alert-queue", message);
    }
}
