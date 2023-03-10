package com.ganga.feign.config;

import com.ganga.feign.fallback.UserClientFallbackFactory;
import feign.Logger;
import org.springframework.context.annotation.Bean;

public class DefaultFeignConfiguration {

    //也可以基于Java代码来修改日志级别，先声明一个类，然后声明一个Logger.Level的对象：
    @Bean
    public Logger.Level logLevel(){
        return Logger.Level.BASIC;
    }

    /**
     * Sentinel 整合 Feign 实现 [降级] / [隔离] 时
     * 给Feign的 UserClient 编写失败后的降级逻辑
     *
     * @return 自定义失败后的降级逻辑
     */
    @Bean
    public UserClientFallbackFactory userClientFallbackFactory(){
        return new UserClientFallbackFactory();
    }

    //如果要 全局生效，将其放到启动类的@EnableFeignClients这个注解中：
    //@EnableFeignClients(defaultConfiguration = DefaultFeignConfiguration .class)

    //如果是 局部生效，则把它放到对应的@FeignClient这个注解中：
    //@FeignClient(value = "userservice", configuration = DefaultFeignConfiguration .class)
}
