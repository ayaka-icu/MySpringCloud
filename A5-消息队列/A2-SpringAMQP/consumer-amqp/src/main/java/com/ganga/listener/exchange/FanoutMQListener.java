package com.ganga.listener.exchange;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FanoutMQListener {

    @RabbitListener(queues = "fanout.queue01")
    public void listenFanoutQueue01(String msg){
        System.out.println("queue [01] 队列获取消息: " + msg);
    }

    @RabbitListener(queues = "fanout.queue02")
    public void listenFanoutQueue02(String msg){
        System.out.println("queue [02] 队列获取消息: " + msg);
    }
}
