package com.ganga.order;

import com.ganga.feign.client.AccountClient;
import com.ganga.feign.client.StorageClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@MapperScan("com.ganga.order.mapper")
@EnableFeignClients(basePackageClasses = {
      AccountClient.class,
      StorageClient.class
})
@SpringBootApplication
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

}
