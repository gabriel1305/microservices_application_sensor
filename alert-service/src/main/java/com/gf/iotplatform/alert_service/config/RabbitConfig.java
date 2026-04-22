package com.gf.iotplatform.alert_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue queue() {
        return new Queue("alert-queue", true);
    }
}
