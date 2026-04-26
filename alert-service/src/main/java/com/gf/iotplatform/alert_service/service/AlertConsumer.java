package com.gf.iotplatform.alert_service.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AlertConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = "alert-queue")
    public void receive(String message) {
        try {
            JsonNode json = objectMapper.readTree(message);
            double temperature = json.get("temperature").asDouble();

            // 🔥 Condicional adicionada
            if (temperature > 35) {
                System.out.println("🚨 ALERTA RECEBIDO: " + message);
            }

        } catch (Exception e) {
            System.out.println("Erro ao processar mensagem: " + message);
        }
    }
}