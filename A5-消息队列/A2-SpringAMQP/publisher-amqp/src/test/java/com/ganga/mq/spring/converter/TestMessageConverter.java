package com.ganga.mq.spring.converter;

import com.ganga.PublisherApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PublisherApp.class)
public class TestMessageConverter {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendMap() {
        HashMap<String, Object> msg = new HashMap<>();
        msg.put("name","ganga");
        msg.put("age",9);
        rabbitTemplate.convertAndSend("object.queue",msg);
    }

}
