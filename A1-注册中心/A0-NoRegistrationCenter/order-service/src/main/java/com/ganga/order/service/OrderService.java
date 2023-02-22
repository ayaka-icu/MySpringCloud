package com.ganga.order.service;

import com.ganga.order.mapper.OrderMapper;
import com.ganga.order.pojo.Order;
import com.ganga.order.pojo.User;
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

    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
        if (ObjectUtils.isEmpty(order)){
            return null;
        }
        // 2.获取用户id
        Long userId = order.getUserId();
        // 3.远程调用 user-service接口
        User user = restTemplate.getForObject("http://localhost:8081/user/" + userId, User.class);
        // 4.从新封装 并 返回
        order.setUser(user);
        return order;
    }
}
