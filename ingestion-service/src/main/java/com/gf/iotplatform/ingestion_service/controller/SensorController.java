package com.gf.iotplatform.ingestion_service.controller;
import com.gf.iotplatform.ingestion_service.model.SensorData;
import com.gf.iotplatform.ingestion_service.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    @PostMapping
    public String receiveSensorData(@RequestBody SensorData data) {

        sensorService.processSensorData(data);

        return "Dados recebidos com sucesso!";
    }
    @GetMapping
        public String test() {
    return "API funcionando!";
}
}