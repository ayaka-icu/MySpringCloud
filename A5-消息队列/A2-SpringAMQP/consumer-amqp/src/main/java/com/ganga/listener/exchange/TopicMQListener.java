package com.ganga.listener.exchange;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

// #.loli
// *.yvjie
// jk.#
// cos.*
// loli.#
@Component
public class TopicMQListener {

    // #.loli
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue01"),
            exchange = @Exchange(name = "topicName", type = ExchangeTypes.TOPIC),
            key = {"#.loli"}
    ))
    public void listenTopicExchangeQueue01(String msg) {
        System.out.println("Queue[01]  key为: [ \"#.loli\" ] 接收到的消息是：" + msg);
    }


    // *.yvjie
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue02"),
            exchange = @Exchange(name = "topicName", type = ExchangeTypes.TOPIC),
            key = {"*.yvjie"}
    ))
    public void listenTopicExchangeQueue02(String msg) {
        System.out.println("Queue[02]  key为: [ \"*.yvjie\" ] 接收到的消息是：" + msg);
    }


    // jk.#
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue03"),
            exchange = @Exchange(name = "topicName", type = ExchangeTypes.TOPIC),
            key = {"jk.#"}
    ))
    public void listenTopicExchangeQueue03(String msg) {
        System.out.println("Queue[03]  key为: [ \"jk.#\" ] 接收到的消息是：" + msg);
    }


    // cos.*
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue04"),
            exchange = @Exchange(name = "topicName", type = ExchangeTypes.TOPIC),
            key = {"cos.*"}
    ))
    public void listenTopicExchangeQueue04(String msg) {
        System.out.println("Queue[04]  key为: [ \"cos.*\" ] 接收到的消息是：" + msg);
    }


    // loli.#
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue05"),
            exchange = @Exchange(name = "topicName", type = ExchangeTypes.TOPIC),
            key = {"loli.#"}
    ))
    public void listenTopicExchangeQueue05(String msg) {
        System.out.println("Queue[05]  key为: [ \"loli.#\" ] 接收到的消息是：" + msg);
    }

}
