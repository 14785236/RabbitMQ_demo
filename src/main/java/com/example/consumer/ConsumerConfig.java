package com.example.consumer;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ConsumerConfig 配置类用于配置消费者相关的监听容器。
 * 该配置类定义了一个 SimpleMessageListenerContainer 实例，用于监听指定的队列，并处理接收到的消息。
 */
@Configuration
public class ConsumerConfig {

    @Autowired
    private QueueMessageListener queueMessageListener;

    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;

    /**
     * 配置并创建一个 SimpleMessageListenerContainer 实例。
     *
     * @return 配置好的 SimpleMessageListenerContainer 对象
     */
    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        // 创建一个 SimpleMessageListenerContainer 实例
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(cachingConnectionFactory);

        // 设置消息确认模式为手动
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);

        // 设置并发消费者的数量
        container.setConcurrentConsumers(10);

        // 设置最大并发消费者的数量
        container.setMaxConcurrentConsumers(10);

        // 设置监听的队列名称
        container.setQueueNames("queue1", "queue2"); // 可以根据需要添加更多队列名称

        // 设置消息监听器，用于处理从队列接收到的消息
        container.setMessageListener(queueMessageListener);

        // 启动监听容器
        container.start();

        // 返回配置好的监听容器
        return container;
    }
}
