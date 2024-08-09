package com.laofansay.work.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String DELAYED_QUEUE = "delayed.queue";
    public static final String DELAYED_EXCHANGE = "delayed.exchange";

    @Bean
    public Queue delayedQueue() {
        return new Queue(DELAYED_QUEUE);
    }

    @Bean
    public CustomExchange delayedExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(DELAYED_EXCHANGE, "x-delayed-message", true, false, args);
    }

    @Bean
    public Binding binding(Queue delayedQueue, CustomExchange delayedExchange) {
        return BindingBuilder.bind(delayedQueue).to(delayedExchange).with(DELAYED_QUEUE).noargs();
    }
}
