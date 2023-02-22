package com.ganga.feign.clients;

import com.ganga.feign.clients.config.FeignConfig;
import com.ganga.feign.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//指定远程调用的 服务名称
@FeignClient(value = "userservice" ,configuration = FeignConfig.class)// 局部生效
public interface UserClient {

    @GetMapping("user/{id}")
    User findById(@PathVariable("id") Long id);

}
