package com.gf.iotplatform.ingestion_service.service;
import com.gf.iotplatform.ingestion_service.model.SensorData;
import org.springframework.stereotype.Service;

@Service
public class SensorService {

    private final AlertPublisher alertPublisher;

    // 🔥 Injeção de dependência
    public SensorService(AlertPublisher alertPublisher) {
        this.alertPublisher = alertPublisher;
    }

    public void processSensorData(SensorData data) {

        System.out.println("📡 Dados recebidos:");
        System.out.println("Sensor: " + data.getSensorId());
        System.out.println("Temperatura: " + data.getTemperature());
        System.out.println("Timestamp: " + data.getTimestamp());

        // 🎯 REGRA DE NEGÓCIO
        if (data.getTemperature() > 35) {

            String message = String.format(
                "🚨 ALERTA - Sensor: %s | Temperatura: %.2f",
                data.getSensorId(),
                data.getTemperature()
            );

            // 🚀 Envia para o RabbitMQ
            alertPublisher.sendAlert(message);
        }
    }
}