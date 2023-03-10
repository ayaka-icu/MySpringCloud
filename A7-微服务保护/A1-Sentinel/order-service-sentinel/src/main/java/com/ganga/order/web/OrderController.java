package com.ganga.order.web;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.ganga.order.entity.Order;
import com.ganga.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController {

   @Autowired
   private OrderService orderService;

    //[热点参数限流]对默认的SpringMVC资源无效，需要利用@SentinelResource注解标记资源
    @SentinelResource("hot") //如果是普通限流模式 可以不加这个注解
    @GetMapping("{orderId}")
    public Order queryOrderByUserId(@PathVariable("orderId") Long orderId) throws InterruptedException {
        // id -> 106 测试 慢调用比例
        // id -> 107 测试 异常比例/异常数
        // 注意：sentinel根据异常比例进行熔断降级，异常是被调用者的异常，而不是调用者的异常
        return orderService.queryOrderById(orderId);
    }

    //模拟订单更新
    @GetMapping("/update")
    public String updateTest(){
        System.err.println("更新商品成功！");;
        return "订单更新成功！";
    }

    //模拟订单查询
    @GetMapping("/query")
    public String queryTest(){
        orderService.queryGoods();
        System.err.println("查询商品成功！\n");;
        return "查询订单成功！";
    }

    //模拟下单
    @GetMapping("/save")
    public String saveTest(){
        orderService.queryGoods();
        System.err.println("下单成功！");;
        return "下单成功！";
    }

}
