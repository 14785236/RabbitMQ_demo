# RabbitMQ Spring Boot 案例

这个项目是一个使用 Spring Boot 和 RabbitMQ 的示例应用程序。演示了如何通过生产者发送消息、通过消费者接收消息，并提供了对 RabbitMQ 资源（如队列、交换器和绑定）的动态管理功能。

## 项目结构

- **ApplicationStart**: 应用程序的启动类，用于启动 Spring Boot 应用。
- **DynamicRabbitMQConfig**: 动态配置 RabbitMQ 的类。
- **RabbitMQController**: 提供 RESTful 接口来管理 RabbitMQ 资源和发送/接收消息。
- **MessageProducer**: 生产者类，用于发送消息到 RabbitMQ。
- **ProducerConfig**: 配置生产者相关的队列和交换器。
- **QueueMessageListener**: 消费者类，处理从 RabbitMQ 接收到的消息。
- **ConsumerConfig**: 配置消费者相关的监听容器。
- **RabbitMQUtil**: 工具类，封装 RabbitMQ 的操作。

## 使用指南

### 环境

- JDK 1.8.0_181
- Maven 3.6.3
- RabbitMQ 服务器

### 安装与运行

1. 克隆项目到本地：
    ```bash
    git clone https://github.com/yourusername/rabbitmq-spring-boot-example.git
    cd rabbitmq-spring-boot-example
    ```

2. 使用 Maven 编译项目：
    ```bash
    mvn clean install
    ```

3. 启动 Spring Boot 应用程序：
    ```bash
    mvn spring-boot:run
    ```

### 配置

配置文件位于 `src/main/resources/application.properties`，你可以根据需要修改 RabbitMQ 的连接信息。

### 功能简介

- **消息发送和接收**: 生产者发送消息到 RabbitMQ，消费者从队列中接收并处理消息。
- **队列管理**: 动态创建、删除队列。
- **交换器管理**: 动态创建、删除交换器。
- **绑定管理**: 动态创建、删除队列与交换器之间的绑定。
- **消息确认**: 发送消息时获取确认结果。


