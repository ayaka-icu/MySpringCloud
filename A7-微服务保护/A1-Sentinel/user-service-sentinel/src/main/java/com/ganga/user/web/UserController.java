package com.ganga.user.web;

import com.ganga.user.pojo.User;
import com.ganga.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 路径： /user/110
     *
     * @param id 用户id
     * @return 用户
     */
    @GetMapping("/{id}")
    public User queryById(@PathVariable("id") Long id) throws InterruptedException {

        //测试熔断降级
        if (id == 6L){
            //慢调用比例
            Thread.sleep(60);
        }else if (id == 7L){
            //异常比例 / 异常数
            throw new RuntimeException("故意抛出异常，触发异常比例熔断");
        }

        return userService.queryById(id);
    }
}
