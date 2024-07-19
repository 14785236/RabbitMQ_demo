package com.example.producer;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.BindingBuilder;

/**
 * ProducerConfig 配置类用于配置生产者相关的队列和交换器。
 * 该配置类定义了一个 DirectExchange 和两个队列，并将这些队列与交换器绑定。
 * 可以从数据库获取动态队列并循环绑定，目前只是当测试案例用
 */
@Configuration
public class ProducerConfig {

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("directExchange");
    }

    @Bean
    public Queue queue1() {
        return new Queue("queue1");
    }

    @Bean
    public Queue queue2() {
        return new Queue("queue2");
    }

    /**
     * 配置交换器和队列的绑定关系。
     */
    @Bean
    public void configureBindings() {
        // 创建 DirectExchange 实例
        DirectExchange directExchange = directExchange();

        // 创建队列实例
        Queue queue1 = queue1();
        Queue queue2 = queue2();

        // 配置交换器和队列的绑定
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue1).to(directExchange).with("routingKey1"));
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue2).to(directExchange).with("routingKey2"));
    }
}
