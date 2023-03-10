package com.ganga.feign.fallback;

import com.ganga.feign.clients.UserClient;
import com.ganga.feign.pojo.User;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserClientFallbackFactory implements FallbackFactory<UserClient> {

    /**
     * Sentinel 整合 Feign 实现 [降级] / [隔离] 时
     * 给Feign的 UserClient 编写失败后的降级逻辑
     * @param throwable
     * @return
     */
    //实现 FallbackFactory<UserClient> --> UserClint 中的方法
    @Override
    public UserClient create(Throwable throwable) {
        //返回创建一个UserClient 并实现其中所有的API
        //因为这里就一个方法，用了λ，实际开发中很少有就1个api的情况
        return id -> {
            log.error("查询用户异常",throwable);
            return new User();
        };
    }
}
