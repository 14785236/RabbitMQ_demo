package com.example.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Configuration
public class DynamicRabbitMQConfig {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Bean
    public RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(rabbitTemplate);
    }

    @Service
    public class RabbitMQService {

        @Autowired
        private RabbitAdmin rabbitAdmin;

        /**
         * 创建队列
         *
         * @param queueName 队列名称
         */
        public void createQueue(String queueName) {
            Queue queue = new Queue(queueName, true); // durable queue
            rabbitAdmin.declareQueue(queue);
        }

        /**
         * 创建交换器
         *
         * @param exchangeName 交换器名称
         */
        public void createExchange(String exchangeName) {
            TopicExchange exchange = new TopicExchange(exchangeName);
            rabbitAdmin.declareExchange(exchange);
        }

        /**
         * 创建绑定
         *
         * @param queueName    队列名称
         * @param exchangeName 交换器名称
         * @param routingKey   路由键
         */
        public void createBinding(String queueName, String exchangeName, String routingKey) {
            Queue queue = new Queue(queueName, true);
            TopicExchange exchange = new TopicExchange(exchangeName);
            Binding binding = BindingBuilder.bind(queue).to(exchange).with(routingKey);
            rabbitAdmin.declareQueue(queue);
            rabbitAdmin.declareExchange(exchange);
            rabbitAdmin.declareBinding(binding);
        }

        /**
         * 删除队列
         *
         * @param queueName 队列名称
         */
        public void deleteQueue(String queueName) {
            rabbitAdmin.deleteQueue(queueName);
        }

        /**
         * 删除交换器
         *
         * @param exchangeName 交换器名称
         */
        public void deleteExchange(String exchangeName) {
            rabbitAdmin.deleteExchange(exchangeName);
        }

        /**
         * 删除绑定
         *
         * @param queueName    队列名称
         * @param exchangeName 交换器名称
         * @param routingKey   路由键
         */
        public void deleteBinding(String queueName, String exchangeName, String routingKey) {
            Queue queue = new Queue(queueName, true);
            TopicExchange exchange = new TopicExchange(exchangeName);
            Binding binding = BindingBuilder.bind(queue).to(exchange).with(routingKey);
            rabbitAdmin.removeBinding(binding);
        }
    }
}
