package com.example.util;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RabbitMQUtil implements ConfirmCallback, ReturnCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    /**
     * 发送消息到指定的交换器和路由键
     *
     * @param exchange   交换器名称
     * @param routingKey 路由键
     * @param message    消息内容
     */
    public void sendMessage(String exchange, String routingKey, Object message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

    /**
     * 从指定队列接收消息
     *
     * @param queueName 队列名称
     * @return 接收到的消息
     */
    public Object receiveMessage(String queueName) {
        return rabbitTemplate.receiveAndConvert(queueName);
    }

    /**
     * 发送消息并获取确认结果
     *
     * @param exchange        交换器名称
     * @param routingKey      路由键
     * @param message         消息内容
     * @param correlationData 用于跟踪消息的唯一ID
     */
    public void sendMessageWithConfirmation(String exchange, String routingKey, Object message, CorrelationData correlationData) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message, correlationData);
    }

    /**
     * 获取队列中消息的数量
     *
     * @param queueName 队列名称
     * @return 队列中的消息数量
     */
    public int getQueueMessageCount(String queueName) {
        return rabbitTemplate.execute(channel -> (int) channel.messageCount(queueName));
    }

    /**
     * 检查队列是否存在消息
     *
     * @param queueName 队列名称
     * @return 队列中是否存在消息
     */
    public boolean queueHasMessages(String queueName) {
        return getQueueMessageCount(queueName) > 0;
    }

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

    /**
     * 确认回调方法，用于处理消息确认。
     *
     * @param correlationData 消息的相关数据，用于回调。
     * @param ack             如果消息被确认，则为true，否则为false。
     * @param cause           否定确认的原因（如果有）。
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            System.out.println("消息已确认: " + correlationData);
        } else {
            System.out.println("消息未确认: " + correlationData + ", 原因: " + cause);
        }
    }

    /**
     * 返回回调方法，用于处理被返回的消息。
     *
     * @param message    被返回的消息。
     * @param replyCode  回复码。
     * @param replyText  回复文本。
     * @param exchange   交换器名称。
     * @param routingKey 路由键。
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("返回的消息: " + message + ", 回复码: " + replyCode + ", 回复文本: " + replyText + ", 交换器: " + exchange + ", 路由键: " + routingKey);
    }
}
