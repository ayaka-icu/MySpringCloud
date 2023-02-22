package com.ganga.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@MapperScan("com.ganga.order.mapper")
@SpringBootApplication
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    /**
     *      注入: RestTemplate
     *<br>      用于发送 Http 请求
     *<br>      远程调用提供者
     */
    @Bean
    @LoadBalanced //加上负载均衡
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}