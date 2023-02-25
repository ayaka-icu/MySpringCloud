package com.ganga.listener.exchange;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DirectMQListener {

    // 基于注解声明队列和交换机
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue01"),
            exchange = @Exchange(name = "directName",type = ExchangeTypes.DIRECT),
            key = {"jk"}
    ))
    public void listenDirectExchangeQueue01(String msg) {
        System.out.println("喜欢 [jk] 的狗接收到消息: " + msg);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue02"),
            exchange = @Exchange(name = "directName",type = ExchangeTypes.DIRECT),
            key = {"loli"} //要监听建为 jk 和 loli 的消息
    ))
    public void listenDirectExchangeQueue02(String msg) {
        System.out.println("喜欢 [loli] 的狗接收到消息: " + msg);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue03"),
            exchange = @Exchange(name = "directName",type = ExchangeTypes.DIRECT),
            key = {"jk","loli"} //要监听建为 jk 和 loli 的消息
    ))
    public void listenDirectExchangeQueue03(String msg) {
        System.err.println("既喜欢 [jk] 又喜欢 [loli] 的畜牲接收到消息: " + msg);
    }

}
