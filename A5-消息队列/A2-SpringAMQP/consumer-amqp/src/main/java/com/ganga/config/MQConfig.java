package com.ganga.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    @Bean
    public Queue simpleQueue(){
        return new Queue("work.queue");
    }
    @Bean
    public Queue workQueue(){
        return new Queue("work.queue");
    }
    @Bean
    public Queue objectQueue(){
        return new Queue("object.queue");
    }

}
