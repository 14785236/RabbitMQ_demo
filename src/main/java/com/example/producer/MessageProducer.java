package com.example.producer;

import com.example.util.RabbitMQUtil;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * MessageProducer 是一个生产者类，用于发送消息到 RabbitMQ。
 */
@Service
public class MessageProducer {

    @Autowired
    private RabbitMQUtil rabbitMQUtil;

    /**
     * 发送消息到指定的路由键。
     *
     * @param exchange   交换器名称
     * @param routingKey 路由键
     * @param message    消息内容
     */
    public void sendMessage(String exchange, String routingKey, Object message) {
        rabbitMQUtil.sendMessage(exchange, routingKey, message);
    }

    /**
     * 发送消息并获取确认结果。
     *
     * @param exchange        交换器名称
     * @param routingKey      路由键
     * @param message         消息内容
     * @param correlationData 用于跟踪消息的唯一ID
     */
    public void sendMessageWithConfirmation(String exchange, String routingKey, Object message, CorrelationData correlationData) {
        rabbitMQUtil.sendMessageWithConfirmation(exchange, routingKey, message, correlationData);
    }
}
