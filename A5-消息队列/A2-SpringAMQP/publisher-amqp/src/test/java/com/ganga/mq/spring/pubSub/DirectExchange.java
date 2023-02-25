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
public class DirectExchange {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testDirectExchangeQueue01(){
        //向 交换机 上发送消息
        String exchangeName = "directName";
        String key = "jk";
        rabbitTemplate.convertAndSend(exchangeName,key,"jk: 放学后要做些什么呢？♥~");
    }

    @Test
    public void testDirectExchangeQueue02(){
        //向 交换机 上发送消息
        String exchangeName = "directName";
        String key = "loli";
        rabbitTemplate.convertAndSend(exchangeName,key,"loli: 欧尼酱陪我玩！♥~");
    }

}
