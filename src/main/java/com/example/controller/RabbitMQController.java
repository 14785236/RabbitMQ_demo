package com.example.controller;

import com.example.util.RabbitMQUtil;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rabbitmq")
public class RabbitMQController {
    @Autowired
    private RabbitMQUtil rabbitMQUtil;

    /**
     * 创建一个新的队列
     *
     * @param queueName 要创建的队列名称
     * @return 创建结果的消息
     */
    @PostMapping("/createQueue")
    public String createQueue(@RequestParam String queueName) {
        rabbitMQUtil.createQueue(queueName);
        return "创建队列: " + queueName;
    }

    /**
     * 创建一个新的交换器
     *
     * @param exchangeName 要创建的交换器名称
     * @return 创建结果的消息
     */
    @PostMapping("/createExchange")
    public String createExchange(@RequestParam String exchangeName) {
        rabbitMQUtil.createExchange(exchangeName);
        return "创建交换器: " + exchangeName;
    }

    /**
     * 创建一个新的绑定
     *
     * @param queueName 要绑定的队列名称
     * @param exchangeName 绑定的交换器名称
     * @param routingKey 路由键
     * @return 创建结果的消息
     */
    @PostMapping("/createBinding")
    public String createBinding(@RequestParam String queueName, @RequestParam String exchangeName, @RequestParam String routingKey) {
        rabbitMQUtil.createBinding(queueName, exchangeName, routingKey);
        return "绑定: queue=" + queueName + ", exchange=" + exchangeName + ", routingKey=" + routingKey;
    }

    /**
     * 删除指定的队列
     *
     * @param queueName 要删除的队列名称
     * @return 删除结果的消息
     */
    @PostMapping("/deleteQueue")
    public String deleteQueue(@RequestParam String queueName) {
        rabbitMQUtil.deleteQueue(queueName);
        return "删除队列: " + queueName;
    }

    /**
     * 删除指定的交换器
     *
     * @param exchangeName 要删除的交换器名称
     * @return 删除结果的消息
     */
    @PostMapping("/deleteExchange")
    public String deleteExchange(@RequestParam String exchangeName) {
        rabbitMQUtil.deleteExchange(exchangeName);
        return "删除交换器: " + exchangeName;
    }

    /**
     * 删除指定的绑定
     *
     * @param queueName 要解绑的队列名称
     * @param exchangeName 解绑的交换器名称
     * @param routingKey 路由键
     * @return 删除结果的消息
     */
    @PostMapping("/deleteBinding")
    public String deleteBinding(@RequestParam String queueName, @RequestParam String exchangeName, @RequestParam String routingKey) {
        rabbitMQUtil.deleteBinding(queueName, exchangeName, routingKey);
        return "删除绑定: queue=" + queueName + ", exchange=" + exchangeName + ", routingKey=" + routingKey;
    }

    /**
     * 发送消息到指定的交换器和路由键
     *
     * @param exchange 交换器名称
     * @param routingKey 路由键
     * @param message 消息内容
     * @return 发送结果的消息
     */
    @PostMapping("/send")
    public String sendMessage(@RequestParam String exchange, @RequestParam String routingKey, @RequestParam String message) {
        rabbitMQUtil.sendMessage(exchange, routingKey, message);
        System.out.println("发送消息为"+message);
        return "交换器："+exchange+"键"+routingKey+"发送消息: " + message;
    }

    /**
     * 发送消息并获取确认结果
     *
     * @param exchange 交换器名称
     * @param routingKey 路由键
     * @param message 消息内容
     * @param correlationData 用于跟踪消息的唯一ID
     * @return 发送结果的消息
     */
    @PostMapping("/sendWithConfirmation")
    public String sendMessageWithConfirmation(@RequestParam String exchange, @RequestParam String routingKey, @RequestParam String message, @RequestParam String correlationId) {
        CorrelationData correlationData = new CorrelationData(correlationId);
        rabbitMQUtil.sendMessageWithConfirmation(exchange, routingKey, message, correlationData);
        return "Message sent with confirmation: " + message + ", Correlation ID: " + correlationId;
    }

    /**
     * 从指定队列接收消息
     *
     * @param queueName 队列名称
     * @return 接收到的消息
     */
    @GetMapping("/receive")
    public String receiveMessage(@RequestParam String queueName) {
        Object message = rabbitMQUtil.receiveMessage(queueName);
        System.out.println("消息："+message);
        return message != null ? "Received message: " + message : "No messages in queue";
    }

    /**
     * 检查指定队列是否有消息
     *
     * @param queueName 队列名称
     * @return 是否有消息
     */
    @GetMapping("/hasMessages")
    public boolean queueHasMessages(@RequestParam String queueName) {
        return rabbitMQUtil.queueHasMessages(queueName);
    }

    /**
     * 获取指定队列中的消息数量
     *
     * @param queueName 队列名称
     * @return 队列中的消息数量
     */
    @GetMapping("/messageCount")
    public int getQueueMessageCount(@RequestParam String queueName) {
        return rabbitMQUtil.getQueueMessageCount(queueName);
    }
}
