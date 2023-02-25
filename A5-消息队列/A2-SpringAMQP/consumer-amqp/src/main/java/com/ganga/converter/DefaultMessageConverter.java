package com.ganga.converter;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;

//将 JacksonMessageConverter 的 @Configuration注释掉 使用默认 消息转换器进行接收
@Component
public class DefaultMessageConverter {

    @RabbitListener(queues = "object.queue")
    public void listenObjectQueue(HashMap<String, Object> msg){
        System.out.println("收到消息：【" + msg + "】");
    }

}
