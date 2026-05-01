package com.gf.iotplatform.ingestion_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    // =========================
    // FILA / EXCHANGE / ROUTING
    // =========================

    public static final String QUEUE = "historical-temp-queue";
    public static final String EXCHANGE = "temperature.received.event";
    public static final String ROUTING_KEY = "temperature-received";

    // =========================
    // CONVERSOR JSON
    // =========================

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // =========================
    // RABBIT TEMPLATE
    // =========================

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         MessageConverter messageConverter) {

        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }

    // =========================
    // DECLARAÇÃO AUTOMÁTICA RABBITMQ
    // =========================

    @Bean
    public Queue historicalTempQueue() {
        return QueueBuilder.durable(QUEUE).build();
    }

    @Bean
    public DirectExchange temperatureExchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding bindingHistoricalTempQueue(Queue historicalTempQueue,
                                              DirectExchange temperatureExchange) {

        return BindingBuilder
                .bind(historicalTempQueue)
                .to(temperatureExchange)
                .with(ROUTING_KEY);
    }
}