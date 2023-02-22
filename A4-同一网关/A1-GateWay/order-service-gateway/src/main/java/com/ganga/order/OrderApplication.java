package com.ganga.order;

import com.alibaba.cloud.nacos.ribbon.NacosRule;
import com.ganga.feign.clients.UserClient;
import com.ganga.feign.clients.config.FeignConfig;
import com.netflix.loadbalancer.IRule;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

//开启Feign自动装配功能
//因为是导入的api 需要扫描对应的包 这可以指定对应的字节码
@EnableFeignClients(clients = {UserClient.class},defaultConfiguration = FeignConfig.class)
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
