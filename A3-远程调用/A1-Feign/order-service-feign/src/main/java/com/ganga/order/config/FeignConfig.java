package com.ganga.order.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

public class FeignConfig {

    //也可以基于Java代码来修改日志级别，先声明一个类，然后声明一个Logger.Level的对象：
    public class DefaultFeignConfiguration  {
        @Bean
        public Logger.Level feignLogLevel(){
            return Logger.Level.BASIC; // 日志级别为BASIC
        }
    }

    //如果要 全局生效，将其放到启动类的@EnableFeignClients这个注解中：
    //@EnableFeignClients(defaultConfiguration = DefaultFeignConfiguration .class)

    //如果是 局部生效，则把它放到对应的@FeignClient这个注解中：
    //@FeignClient(value = "userservice", configuration = DefaultFeignConfiguration .class)
}
