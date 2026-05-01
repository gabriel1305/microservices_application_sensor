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
    private Long eventTimestamp;

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

    public Long getEventTimestamp() {
        return eventTimestamp;
    }

    public void setEventTimestamp(Long eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
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

    @Column(name = "sensor_id", nullable = false)
    private String sensorId;

    @Column(name = "temp_value", nullable = false)
    private Double temperature;

    @Column(name = "event_timestamp", nullable = false)
    private Long eventTimestamp;

    public Long getId() {
        return id;
    }

    public String getSensorId() {
        return sensorId;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Long getEventTimestamp() {
        return eventTimestamp;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public void setEventTimestamp(Long eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
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

        // 🔥 CORREÇÃO PRINCIPAL AQUI
        Long timestamp = data.getEventTimestamp();

        if (timestamp == null) {
            timestamp = System.currentTimeMillis(); // fallback seguro
        }

        entity.setEventTimestamp(timestamp);

        repository.save(entity);

        System.out.println("✅ Dados salvos no PostgreSQL!");
    }
}