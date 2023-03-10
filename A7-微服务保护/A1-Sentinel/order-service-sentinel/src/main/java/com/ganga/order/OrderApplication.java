package com.ganga.order;

import com.ganga.feign.clients.UserClient;
import com.ganga.feign.config.DefaultFeignConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

//开启Feign自动装配功能
//因为是导入的api 需要扫描对应的包 这可以指定对应的字节码
@EnableFeignClients(clients = UserClient.class,defaultConfiguration = DefaultFeignConfiguration.class)
@MapperScan("com.ganga.order.mapper")
@SpringBootApplication
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

}
