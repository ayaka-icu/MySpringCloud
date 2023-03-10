package com.ganga.order.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.ganga.feign.clients.UserClient;
import com.ganga.feign.pojo.User;
import com.ganga.order.mapper.OrderMapper;
import com.ganga.order.entity.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private RestTemplate restTemplate;
    @Resource //将 FeignClient 注入进来
    private UserClient userClient;

    //使用 Feige 进行远程调用
    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
        if (ObjectUtils.isEmpty(order)){
            return null;
        }
        // 2.获取用户id
        Long userId = order.getUserId();
        // 3.使用 Feige 进行远程调用
        User user = userClient.findById(userId);
        // 4.从新封装 并 返回
        order.setUser(user);
        return order;
    }

    @SentinelResource("goods")
    public void queryGoods(){
        System.out.println("查询商品成功！");;
    }

}
