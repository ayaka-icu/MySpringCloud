package com.ganga.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class WorkMQListener {

    @RabbitListener(queues = "work.queue")
    public void listenWorkQueue01(String message) throws InterruptedException {
        System.out.println("消费者[1]接收到：" + message + "  " + LocalTime.now());
        Thread.sleep(20);
    }

    @RabbitListener(queues = "work.queue")
    public void listenWorkQueue02(String message) throws InterruptedException {
        System.err.println("消费者[2]接收到：" + message + "  " + LocalTime.now());
        Thread.sleep(300);
    }

}
