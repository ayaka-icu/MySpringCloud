package com.ganga.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutMQConfig {

    //这种通过 @Bean 去声明交换机 和 消息队列 并 进行绑定非常麻烦
    //可以使用 基于注解声明并绑定的方法 见: com.ganga.listener.exchange.DirectMQListener

    @Bean //声明 FanoutExchange 广播交换机
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("fanoutName");
    }

    @Bean //声明消息队列
    public Queue newQueue01(){
        return new Queue("fanout.queue01");
    }

    @Bean //将 消息队列 与 广播交换机 进行绑定
    public Binding fanoutBindingQueue01(){
        //使用 BindingBuilder 构建绑定关系
        return BindingBuilder.bind(newQueue01()).to(fanoutExchange());
    }

    //==================================================================

    @Bean //声明消息队列
    public Queue newQueue02(){
        return new Queue("fanout.queue02");
    }

    @Bean //将 消息队列 与 广播交换机 进行绑定
    public Binding fanoutBindingQueue02(){
        //使用 BindingBuilder 构建绑定关系
        return BindingBuilder.bind(newQueue02()).to(fanoutExchange());
    }

}
