package com.ganga.order.clients;

import com.ganga.order.config.FeignConfig;
import com.ganga.order.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//指定远程调用的 服务名称
@FeignClient(value = "userservice" ,configuration = FeignConfig.class)// 局部生效
public interface FeigeClient {

    @GetMapping("user/{id}")
    User findById(@PathVariable("id") Long id);

}
