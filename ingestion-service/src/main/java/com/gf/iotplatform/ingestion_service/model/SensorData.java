package com.gf.iotplatform.ingestion_service.model;

public class SensorData {

    private String sensorId;
    private Double temperature;
    private Long timestamp;

    // Construtor vazio (necessário pro Spring)
    public SensorData() {}

    // Getters e Setters

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