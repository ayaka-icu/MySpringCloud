package com.ganga.mq.spring;

import com.ganga.PublisherApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class) //别忘了这个
@SpringBootTest(classes = PublisherApp.class)
public class WorkMQ {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendSimpleMessageQueue01(){
        String queueName = "work.queue";
        //发送 50条消息
        for (int i = 1; i <= 50; i++) {
            rabbitTemplate.convertAndSend(queueName, "第[" + i + "]条消息");
        }

    }

}

