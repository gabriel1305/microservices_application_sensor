package com.gf.iotplatform.historical_temp_service.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import jakarta.persistence.*;

//////////////////////////////////////////////////////////////////
// 📦 DTO (mensagem do Rabbit)
//////////////////////////////////////////////////////////////////
class SensorData {

    private String sensorId;
    private Double temperature;
    private Long timestamp;

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}

//////////////////////////////////////////////////////////////////
// 🧱 ENTITY (tabela sensordb)
//////////////////////////////////////////////////////////////////
@Entity
@Table(name = "sensordb")
class TemperatureHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sensor_id")
    private String sensorId;

    @Column(name = "temp_value")
    private Double temperature;

    @Column(name = "timestamp")
    private Long timestamp;

    public Long getId() {
        return id;
    }

    public String getSensorId() {
        return sensorId;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}

//////////////////////////////////////////////////////////////////
// 🗂️ REPOSITORY
//////////////////////////////////////////////////////////////////
@Repository
interface TemperatureRepository extends JpaRepository<TemperatureHistory, Long> {
}

//////////////////////////////////////////////////////////////////
// 🔥 CONSUMER (Rabbit Listener)
//////////////////////////////////////////////////////////////////
@Service
class TemperatureConsumer {

    private final TemperatureRepository repository;

    public TemperatureConsumer(TemperatureRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = "historical-temp-queue")
    public void consumir(SensorData data) {

        if (data == null) {
            System.out.println("⚠️ Mensagem nula recebida");
            return;
        }

        System.out.println("📩 Recebido sensor: " + data.getSensorId());

        TemperatureHistory entity = new TemperatureHistory();
        entity.setSensorId(data.getSensorId());
        entity.setTemperature(data.getTemperature());
        entity.setTimestamp(data.getTimestamp());

        repository.save(entity);

        System.out.println("✅ Dados salvos no PostgreSQL!");
    }
}