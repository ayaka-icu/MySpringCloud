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
public class TopicExchange {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // queue01  -->  #.loli
    // queue02  -->  *.yvjie
    // queue03  -->  jk.#
    // queue04  -->  cos.*
    // queue05  -->  loli.#

    @Test
    public void sendTopicExchangeQueue01() {
        String exchangeName = "topicName";
        String key = "jk.loli";
        rabbitTemplate.convertAndSend(exchangeName, key, "一个穿着 [jk] 的 小[loli] ...");
    }
    // Queue[03]  key为: [ "jk.#" ] 接收到的消息是：一个穿着 [jk] 的 小[loli] ...
    // Queue[01]  key为: [ "#.loli" ] 接收到的消息是：一个穿着 [jk] 的 小[loli] ...

    @Test
    public void sendTopicExchangeQueue02() {
        String exchangeName = "topicName";
        String key = "jk.yvjie";
        rabbitTemplate.convertAndSend(exchangeName, key, "一个穿着 [jk] 的 [yvjie] ...");
    }
    // Queue[03]  key为: [ "jk.#" ] 接收到的消息是：一个穿着 [jk] 的 [yvjie] ...
    // Queue[04]  key为: [ "cos.*" ] 接收到的消息是：一个穿着 [jk] 的 [yvjie] ...
    // Queue[02]  key为: [ "*.yvjie" ] 接收到的消息是：一个穿着 [jk] 的 [yvjie] ...


    @Test
    public void sendTopicExchangeQueue03() {
        String exchangeName = "topicName";
        String key = "cos.loli";
        rabbitTemplate.convertAndSend(exchangeName, key, "一个穿着 [cos] 的 [loli] ...");
    }
    // Queue[03]  key为: [ "jk.#" ] 接收到的消息是：一个穿着 [cos] 的 [loli] ...
    // Queue[04]  key为: [ "cos.*" ] 接收到的消息是：一个穿着 [cos] 的 [loli] ...
    // Queue[01]  key为: [ "#.loli" ] 接收到的消息是：一个穿着 [cos] 的 [loli] ...

    @Test
    public void sendTopicExchangeQueue04() {
        String exchangeName = "topicName";
        String key = "cos.yvjie";
        rabbitTemplate.convertAndSend(exchangeName, key, "一个穿着 [cos] 的 [yvjie] ...");
    }
    // Queue[04]  key为: [ "cos.*" ] 接收到的消息是：一个穿着 [cos] 的 [yvjie] ...
    // Queue[03]  key为: [ "jk.#" ] 接收到的消息是：一个穿着 [cos] 的 [yvjie] ...
    // Queue[02]  key为: [ "*.yvjie" ] 接收到的消息是：一个穿着 [cos] 的 [yvjie] ...


}
