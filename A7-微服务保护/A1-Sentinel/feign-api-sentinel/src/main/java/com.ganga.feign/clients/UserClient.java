package com.ganga.feign.clients;

import com.ganga.feign.fallback.UserClientFallbackFactory;
import com.ganga.feign.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//指定远程调用的 服务名称
@FeignClient(value = "userservice" ,fallbackFactory = UserClientFallbackFactory.class)
public interface UserClient {

    @GetMapping("user/{id}")
    User findById(@PathVariable("id") Long id);

}
