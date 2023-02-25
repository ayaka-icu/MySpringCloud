package com.ganga.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

//@Component
public class SimpleMQListener {

    //使用 @RabbitListener 监听消息队列 参数queue表示要监听哪个消息队列
    @RabbitListener(queues = "simple.queue")
    public void listenSimpleQueue(String message/*参数即使监听到的数据*/){
        //将消息打印处理出来
        System.err.println("接收到消息：【" + message + "】");
    }

}
