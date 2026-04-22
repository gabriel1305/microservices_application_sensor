package com.gf.iotplatform.alert_service.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AlertConsumer {

    @RabbitListener(queues = "alert-queue")
    public void receive(String message) {
        System.out.println("🚨 ALERTA RECEBIDO: " + message);
    }
}