
package com.gf.iotplatform.ingestion_service.service;
import com.gf.iotplatform.ingestion_service.model.SensorData;
import org.springframework.stereotype.Service;

@Service
public class SensorService {

    public void processSensorData(SensorData data) {

        System.out.println("📡 Dados recebidos:");
        System.out.println("Sensor: " + data.getSensorId());
        System.out.println("Temperatura: " + data.getTemperature());
        System.out.println("Timestamp: " + data.getTimestamp());

    }
}
