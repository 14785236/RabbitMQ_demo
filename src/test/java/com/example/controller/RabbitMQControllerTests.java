package com.example.controller;


import com.example.util.RabbitMQUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * 测试类，用于验证 RabbitMQController 中的各个端点的功能。
 */
@WebMvcTest(RabbitMQController.class)
public class RabbitMQControllerTests {
    @Autowired
    private MockMvc mockMvc; // MockMvc 用于模拟 HTTP 请求和响应

    @MockBean
    private RabbitMQUtil rabbitMQUtil; // 模拟 RabbitMQUtil

    @InjectMocks
    private RabbitMQController rabbitMQController; // 需要测试的控制器

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this); // 初始化Mockito注解
        mockMvc = MockMvcBuilders.standaloneSetup(rabbitMQController).build(); // 初始化 MockMvc
    }

    /**
     * 测试创建队列的端点。
     *
     * @throws Exception 如果请求处理失败
     */
    @Test
    public void testCreateQueue() throws Exception {
        doNothing().when(rabbitMQUtil).createQueue(anyString()); // 模拟 createQueue 方法

        mockMvc.perform(MockMvcRequestBuilders.post("/rabbitmq/createQueue")
                        .param("queueName", "testQueue")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Queue created: testQueue"));
        System.out.println("创建队列。成功");
    }

    /**
     * 测试创建交换器的端点。
     *
     * @throws Exception 如果请求处理失败
     */
    @Test
    public void testCreateExchange() throws Exception {
        doNothing().when(rabbitMQUtil).createExchange(anyString()); // 模拟 createExchange 方法

        mockMvc.perform(MockMvcRequestBuilders.post("/rabbitmq/createExchange")
                        .param("exchangeName", "testExchange")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Exchange created: testExchange"));
        System.out.println("创建交换器。成功");
    }

    /**
     * 测试创建绑定的端点。
     *
     * @throws Exception 如果请求处理失败
     */
    @Test
    public void testCreateBinding() throws Exception {
        doNothing().when(rabbitMQUtil).createBinding(anyString(), anyString(), anyString()); // 模拟 createBinding 方法

        mockMvc.perform(MockMvcRequestBuilders.post("/rabbitmq/createBinding")
                        .param("queueName", "testQueue")
                        .param("exchangeName", "testExchange")
                        .param("routingKey", "testRoutingKey")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Binding created: queue=testQueue, exchange=testExchange, routingKey=testRoutingKey"));
        System.out.println("创建绑定。成功");
    }

    /**
     * 测试删除队列的端点。
     *
     * @throws Exception 如果请求处理失败
     */
    @Test
    public void testDeleteQueue() throws Exception {
        doNothing().when(rabbitMQUtil).deleteQueue(anyString());

        mockMvc.perform(MockMvcRequestBuilders.post("/rabbitmq/deleteQueue")
                        .param("queueName", "testQueue")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("删除了: testQueue"));
    }

    /**
     * 测试删除交换器的端点。
     *
     * @throws Exception 如果请求处理失败
     */
    @Test
    public void testDeleteExchange() throws Exception {
        doNothing().when(rabbitMQUtil).deleteExchange(anyString());

        mockMvc.perform(MockMvcRequestBuilders.post("/rabbitmq/deleteExchange")
                        .param("exchangeName", "testExchange")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Exchange deleted: testExchange"));
    }

    /**
     * 测试删除绑定的端点。
     *
     * @throws Exception 如果请求处理失败
     */
    @Test
    public void testDeleteBinding() throws Exception {
        doNothing().when(rabbitMQUtil).deleteBinding(anyString(), anyString(), anyString());

        mockMvc.perform(MockMvcRequestBuilders.post("/rabbitmq/deleteBinding")
                        .param("queueName", "testQueue")
                        .param("exchangeName", "testExchange")
                        .param("routingKey", "testRoutingKey")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Binding deleted: queue=testQueue, exchange=testExchange, routingKey=testRoutingKey"));
    }

    /**
     * 测试发送消息的端点。
     *
     * @throws Exception 如果请求处理失败
     */
    @Test
    public void testSendMessage() throws Exception {
        doNothing().when(rabbitMQUtil).sendMessage(anyString(), anyString(), anyString());

        mockMvc.perform(MockMvcRequestBuilders.post("/rabbitmq/send")
                        .param("exchange", "testExchange")
                        .param("routingKey", "testRoutingKey")
                        .param("message", "11111111111111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Message sent: 11111111111111"));
        System.out.println("发送消息。成功");
    }

    /**
     * 测试发送消息并获取确认结果的端点。
     *
     * @throws Exception 如果请求处理失败
     */
    @Test
    public void testSendMessageWithConfirmation() throws Exception {
        doNothing().when(rabbitMQUtil).sendMessageWithConfirmation(anyString(), anyString(), anyString(), any());

        mockMvc.perform(MockMvcRequestBuilders.post("/rabbitmq/sendWithConfirmation")
                        .param("exchange", "testExchange")
                        .param("routingKey", "testRoutingKey")
                        .param("message", "Test message")
                        .param("correlationId", "12345")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Message sent with confirmation: Test message, Correlation ID: 12345"));
    }

    /**
     * 测试从队列接收消息的端点。
     *
     * @throws Exception 如果请求处理失败
     */
    @Test
    public void testReceiveMessage() throws Exception {
        when(rabbitMQUtil.receiveMessage(anyString())).thenReturn("Test message");

        mockMvc.perform(MockMvcRequestBuilders.get("/rabbitmq/receive")
                        .param("queueName", "testQueue")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Received message: Test message"));
        System.out.println("接受消息。成功");
    }

    /**
     * 测试检查队列是否有消息的端点。
     *
     * @throws Exception 如果请求处理失败
     */
    @Test
    public void testQueueHasMessages() throws Exception {
        when(rabbitMQUtil.queueHasMessages(anyString())).thenReturn(true);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/rabbitmq/hasMessages")
                        .param("queueName", "testQueue")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
        System.out.println(resultActions.andReturn().getResponse().getContentAsString());

    }

    /**
     * 测试获取队列消息数量的端点。
     *
     * @throws Exception 如果请求处理失败
     */
    @Test
    public void testGetQueueMessageCount() throws Exception {
        when(rabbitMQUtil.getQueueMessageCount(anyString())).thenReturn(5);

        mockMvc.perform(MockMvcRequestBuilders.get("/rabbitmq/messageCount")
                        .param("queueName", "testQueue")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("5"));
    }
    /**
     * 测试生产者发送消息到队列，消费者从队列接收并处理消息的流程。
     *
     * @throws Exception 如果请求处理失败
     */
    @Test
    public void testSendAndReceiveMessage() throws Exception {
        String queueName = "testQueue";
        String message = "1111111";

        // 设置消息接收模拟
        when(rabbitMQUtil.receiveMessage(anyString())).thenReturn(message);

        // 发送消息
        mockMvc.perform(MockMvcRequestBuilders.post("/rabbitmq/send")
                        .param("exchange", "") // 为空表示直接发送到队列
                        .param("routingKey", queueName)
                        .param("message", message)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Message sent: 1111111"));

        // 接收消息
        mockMvc.perform(MockMvcRequestBuilders.get("/rabbitmq/receive")
                        .param("queueName", queueName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Received message: 1111111"));
    }
}
