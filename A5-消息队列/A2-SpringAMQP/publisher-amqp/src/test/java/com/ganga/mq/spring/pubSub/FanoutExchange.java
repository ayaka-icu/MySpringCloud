package com.ganga.mq.spring.pubSub;

import com.ganga.PublisherApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PublisherApp.class)
public class FanoutExchange {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testFanoutExchangeQueue(){
        String exchangeName = "fanoutName";
        String key = "";
        String message = "消息xxx";
        //在对交换机发送消息时 交换机要提前声明！！！
        //参数第一个是 交换机名称 而不是 消息队列名称！！！
        //因为发送到交换机中 由交换机 进行发送到队列 订阅者绑定的消息队列中进行获取消息
        rabbitTemplate.convertAndSend(exchangeName,key,message);
    }

}
