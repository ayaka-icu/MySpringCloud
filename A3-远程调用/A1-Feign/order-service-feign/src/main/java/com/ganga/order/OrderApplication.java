package com.ganga.order;

import com.alibaba.cloud.nacos.ribbon.NacosRule;
import com.ganga.order.config.FeignConfig;
import com.netflix.loadbalancer.IRule;
import feign.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

//开启Feign自动装配功能
@EnableFeignClients
//@EnableFeignClients(defaultConfiguration = FeignConfig.class) 全局生效
@MapperScan("com.ganga.order.mapper")
@SpringBootApplication
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    /**
     * 负载均衡
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public IRule iRule(){
        return new NacosRule();
    }

}
