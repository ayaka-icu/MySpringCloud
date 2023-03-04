package com.ganga.hotel.config;

import com.ganga.hotel.constant.MqConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {

    /**
     * 自定义 消息转换器
     * @return new Jackson2JsonMessageConverter()
     */
    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    /**
     *
     * @return Topic交换机
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(MqConstant.HOTEL_EXCHANGE, true, false);
    }

    /**
     *
     * @return 新增 / 修改 的消息队列
     */
    @Bean
    public Queue insertQueue() {
        return new Queue(MqConstant.HOTEL_INSERT_QUEUE, true);
    }

    /**
     * 删除 的消息队列
     * @return
     */
    @Bean
    public Queue deleteQueue() {
        return new Queue(MqConstant.HOTEL_DELETE_QUEUE, true);
    }

    /**
     * 消息队列 与 交换机 绑定
     * 注意：别忘了加上 RoutingKey ！！！
     * @return Binding
     */
    @Bean
    public Binding bindingInsertQueue() {
        return BindingBuilder.bind(insertQueue()).to(topicExchange()).with(MqConstant.HOTEL_INSERT_KEY);
    }

    @Bean
    public Binding bindingDeleteQueue(){
        return BindingBuilder.bind(deleteQueue()).to(topicExchange()).with(MqConstant.HOTEL_DELETE_KEY);
    }


}
