package com.ganga.order.web;

import com.ganga.order.pojo.Order;
import com.ganga.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
public class OrderController {

   @Autowired
   private OrderService orderService;

    @GetMapping("{orderId}")
    public Order queryOrderByUserId(@PathVariable("orderId") Long orderId,
                                    @RequestHeader(value = "Header-key",
                                                   required = false) String headerKey) {
        System.out.println("Header-key = " + headerKey);
        // 根据id查询订单并返回
        return orderService.queryOrderById(orderId);
    }
}
