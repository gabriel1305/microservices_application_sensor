package com.gf.iotplatform.ingestion_service.service;

import com.gf.iotplatform.ingestion_service.model.SensorData;
import org.springframework.stereotype.Service;

@Service
public class SensorService {

    private final TemperaturePublisher temperaturePublisher;

    // ✅ Injeção correta
    public SensorService(TemperaturePublisher temperaturePublisher) {
        this.temperaturePublisher = temperaturePublisher;
    }

    public void processSensorData(SensorData data) {

        System.out.println("📡 Dados recebidos:");
        System.out.println("Sensor: " + data.getSensorId());
        System.out.println("Temperatura: " + data.getTemperature());
        System.out.println("Timestamp: " + data.getTimestamp());

        // 🔥 Agora você publica o EVENTO, não o alerta
        String message = String.format(
            "{\"sensorId\":\"%s\",\"temperature\":%.2f,\"timestamp\":%d}",
            data.getSensorId(),
            data.getTemperature(),
            data.getTimestamp()
        );

        // 🚀 Envia para o tópico
        temperaturePublisher.sendTemperature(message);
    }
}