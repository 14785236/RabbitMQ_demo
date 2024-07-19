package com.example.consumer;


import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * QueueMessageListener 是一个消费者类，用于处理从 RabbitMQ 接收到的消息。
 */
@Service
public class QueueMessageListener implements ChannelAwareMessageListener {

    /**
     * 处理接收到的消息。
     *
     * @param message 接收到的消息
     * @param channel 消息通道
     * @throws Exception 处理消息时可能抛出的异常
     */
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        String queue = message.getMessageProperties().getConsumerQueue();
        byte[] body = message.getBody();
        String messageContent = new String(body);
        System.out.println("接收到：" + queue + "，消息内容为：" + messageContent);

        // 根据队列名称处理不同的业务逻辑
        switch (queue) {
            case "queue1":
                handleQueue1Message(messageContent);
                break;
            case "queue2":
                handleQueue2Message(messageContent);
                break;
            // 添加更多队列处理逻辑
            default:
                System.out.println("未处理的队列：" + queue);
                break;
        }

        // 手动确认消息
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        System.out.println(queue + " 队列消息消费成功");
    }

    @Override
    public void onMessage(Message message) {
        ChannelAwareMessageListener.super.onMessage(message);
    }

    @Override
    public void containerAckMode(AcknowledgeMode mode) {
        ChannelAwareMessageListener.super.containerAckMode(mode);
    }

    @Override
    public void onMessageBatch(List<Message> messages) {
        ChannelAwareMessageListener.super.onMessageBatch(messages);
    }

    @Override
    public void onMessageBatch(List<Message> messages, Channel channel) {
        ChannelAwareMessageListener.super.onMessageBatch(messages, channel);
    }

    private void handleQueue1Message(String messageContent) {
        // 处理 queue1 的消息
        System.out.println("处理 queue1 消息：" + messageContent);
    }

    private void handleQueue2Message(String messageContent) {
        // 处理 queue2 的消息
        System.out.println("处理 queue2 消息：" + messageContent);
    }
}
