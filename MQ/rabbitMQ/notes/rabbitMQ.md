















# RabbitMQ

<div align="center"> <img src="rabbitMQ.png" width="50%"/> </div><br>



Table of Contents
-----------------

* [1. 什么是 MQ?](#1-什么是-mq)
   * [1.1 生产者](#11-生产者)
   * [1.2 消费者](#12-消费者)
* [2. 为什么要使用 MQ?](#2-为什么要使用-mq)
   * [2.1 解耦](#21-解耦)
   * [2.2 削峰](#22-削峰)
   * [2.3 数据分发](#23-数据分发)
* [3. 消息队列存在的问题](#3-消息队列存在的问题)
   * [3.1 系统复杂性](#31-系统复杂性)
   * [3.2 数据一致性](#32-数据一致性)
   * [3.3 可用性](#33-可用性)
* [4. 同类产品比较](#4-同类产品比较)
* [5. Preparation](#5-preparation)
   * [5.1 安装 RabbitMQ](#51-安装-rabbitmq)
   * [5.2 添加新用户](#52-添加新用户)
   * [5.3 创建 virtual host](#53-创建-virtual-host)
* [6. Queues in RabbitMQ](#6-queues-in-rabbitmq)
   * [6.1 Names](#61-names)
   * [6.2 Server-named Queues](#62-server-named-queues)
   * [6.3 Properties](#63-properties)
* [7. "Hello World"](#7-hello-world)
* [8. Work queues](#8-work-queues)
* [9. Publish / Subscribe](#9-publish--subscribe)
* [10. Routing](#10-routing)
* [11. Topics](#11-topics)
* [Conclusion](#conclusion)
* [参考资料](#参考资料)



## 1. 什么是 MQ?

<div align="center"> <img src="mq.png" width="50%"/> </div><br>





`MQ` 即为 `message queue`，消息队列是应用程序和应用程序之间的通信方法



### 1.1 生产者

- 生产者：把数据放到队列中的执行者



### 1.2 消费者

- 消费者：将数据从队列中取出的执行者



> **RabbitMQ is a message broker: it accepts and forwards messages. You can think about it as a post office: when you put the mail that you want posting in a post box, you can be sure that Mr. or Ms. Mailperson will eventually deliver the mail to your recipient. In this analogy, RabbitMQ is a post box, a post office and a postman.**



<div align="center"> <img src="new-mq.jpg" width="50%"/> </div><br>



## 2. 为什么要使用 MQ?

- 应用解耦
- 流量削峰
- 数据分发 



### 2.1 解耦

一个系统的耦合度越高，容错性就越低（牵一发而动全身）



解耦是消息队列所要解决最本质的问题。

解耦，一个事务，只关心核心的流程，而需要依赖其他系统但不那么重要的事情，有通知即可，无需等待结构。



举个例子，在一个订单系统中

<div align="center"> <img src="image-20200809151522494.png" width="40%"/> </div><br>

包含以下功能（流程）：

- 订单支付
- 库存
- 物流



若物流系统发生故障



加入消息队列前：

则订单系统需要等待，终端系统等待时间过长会造成用户体验感极差



加入了消息队列后：

用户支付操作正常完成，将数据放到消息队列中，并及时返回“支付成功“的讯号

而当物流系统恢复后，补充处理消息队列中的订单消息即可（异步），用户几乎感受不到



<div align="center"> <img src="image-20200809151754415.png" width="45%"/> </div><br>





### 2.2 削峰

<div align="center"> <img src="image-20200809170311265.png" width="40%"/> </div><br>

在没有引入消息队列前：

若服务器在某一时间段的访问量陡增，例如常见的秒杀，双 11 等，公司原有的服务器承受不了高强度的访问量，会造成数据库崩溃（频繁地与系统 `IO` 打交道）



这时需引入消息队列：

<div align="center"> <img src="image-20200809170207889.png" width="40%"/> </div><br>

起到削峰的作用







### 2.3 数据分发























## 3. 消息队列存在的问题

任何事物都有两面性，在系统引入消息队列也有其缺点：

- 系统复杂性
- 数据一致性
- 可用性



### 3.1 系统复杂性

在系统中引入 `mq` 主要会造成以下问题：

- 重复消费
- 消息丢失
- 消息顺序消费



### 3.2 数据一致性

数据的一致性涉及到分布式事务的知识，广泛存在于分布式系统中

引入消息队列会将这个问题的缺点放大



### 3.3 可用性

如何保证 `mq` 的高可用性？







## 4. 同类产品比较

（实习的时候，公司用的是 `kafka`）

<div align="center"> <img src="111.jpg" width="60%"/> </div><br>




## 5. Preparation

### 5.1 安装 RabbitMQ

采用 `homebrew` 安装 `rabbitmq`

<div align="center"> <img src="image-20200808164521398.png" width="60%"/> </div><br>



通过 `homebrew` 安装的软件位于 `/usr/local/Cellar` 上


<div align="center"> <img src="image-20200808164750922.png" width="60%"/> </div><br>

启动 `rabbitmq-server`


<div align="center"> <img src="image-20200808165033549.png" width="60%"/> </div><br>



输入网址：

```html
http://localhost:15672/
```

<div align="center"> <img src="image-20200808165134031.png" width="50%"/> </div><br>

默认账号密码都为 `guest`

搭建成功

<div align="center"> <img src="image-20200808165224073.png" width="100%"/> </div><br>



### 5.2 添加新用户

设置新账号


<div align="center"> <img src="image-20200808205850672.png" width="100%"/> </div><br>

添加成功！

<div align="center"> <img src="image-20200808205922733.png" width="50%"/> </div><br>









###  5.3 创建 virtual host

<div align="center"> <img src="image-20200810095221458.png" width="90%"/> </div><br>

创建新的 `virtual host`：`myVH`


<div align="center"> <img src="image-20200810095500313.png" width="90%"/> </div><br>


添加 `permission`

<div align="center"> <img src="image-20200810095625133.png" width="90%"/> </div><br>

## 6. Queues in RabbitMQ

在 `rabbitmq` 中，有专门的 `Queue` 类

<div align="center"> <img src="image-20200812120045981.png" width="40%"/> </div><br>

### 6.1 Names

> Queues have names so that applications can reference them.

每个队列都有自己的名字

### 6.2 Server-named Queues

> In AMQP 0-9-1, the broker can generate a unique queue name on behalf of an app. To use this feature, pass an empty string as the queue name argument: The same generated name may be obtained by subsequent methods in the same channel by using the empty string where a queue name is expected. This works because the channel remembers the last server-generated queue name.

队列可以由生产者随机命名



### 6.3 Properties

> Queues have properties that define how they behave. There is a set of mandatory properties and a map of optional ones:
>
> - Name
> - Durable (the queue will survive a broker restart)
> - Exclusive (used by only one connection and the queue will be deleted when that connection closes)
> - Auto-delete (queue that has had at least one consumer is deleted when last consumer unsubscribes)
> - Arguments (optional; used by plugins and broker-specific features such as message TTL, queue length limit, etc)



```java
private int ticket = 0;
private String queue = "";
private boolean passive = false;
private boolean durable = false;
private boolean exclusive = false;
private boolean autoDelete = false;
private boolean nowait = false;
private Map<String,Object> arguments = null;
```







## 7. "Hello World"

<div align="center"> <img src="image-20200810172918581.png" width="50%"/> </div><br>

引入 `maven`

```xml
<dependencies>

  <!-- https://mvnrepository.com/artifact/com.rabbitmq/amqp-client -->
  <dependency>
    <groupId>com.rabbitmq</groupId>
    <artifactId>amqp-client</artifactId>
    <version>5.9.0</version>
  </dependency>

  <!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-api -->
  <dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>1.7.30</version>
  </dependency>

  <!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-simple -->
  <dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-simple</artifactId>
    <version>1.7.30</version>
    <scope>test</scope>
  </dependency>

</dependencies>
```



自定义 `ConnectionFactoryUtil` 工具类

**ConnectionFactoryUtil.java**

```java
/**
 * Connection factory util
 */
public class ConnectionFactoryUtil {
    public static ConnectionFactory getConnectionFactory() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setVirtualHost("/myVH");
        connectionFactory.setUsername("ceezyyy");
        connectionFactory.setPassword("123456");
        return connectionFactory;
    }
}
```



**Publisher.java**

```java
/**
 * Publisher of "hello world"
 */
public class Publisher {
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {

        // get factory
        ConnectionFactory factory = ConnectionFactoryUtil.getConnectionFactory();

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("Message sent!");
        }

    }

}
```

消息发送成功！

<div align="center"> <img src="image-20200811100048282.png" width="80%"/> </div><br>

<div align="center"> <img src="image-20200811100134454.png" width="50%"/> </div><br>

发送完消息，需要定义 `consumer` 接受消息

> That's it for our publisher. Our consumer listens for messages from RabbitMQ, so unlike the publisher which publishes a single message, we'll keep the consumer running to listen for messages and print them out.



**Consumer.java**

```java
/**
 * Consumer of "hello world"
 * <p>
 * we want the process to stay alive while the consumer is listening asynchronously for messages to arrive
 */
public class Consumer {

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = ConnectionFactoryUtil.getConnectionFactory();

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(Publisher.QUEUE_NAME, false, false, false, null);
        System.out.println("Waiting for message");

        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                String receivedMessage = new String(message.getBody());
                System.out.println("Received message: " + receivedMessage);
            }
        };

        CancelCallback cancelCallback = new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {
                System.out.println("Receive failed!");
            }
        };

        channel.basicConsume(Publisher.QUEUE_NAME, deliverCallback, cancelCallback);

    }

}
```

消费成功！

<div align="center"> <img src="image-20200811103051494.png" width="40%"/> </div><br>



## 8. Work queues


<div align="center"> <img src="image-20200811104438473.png" width="40%"/> </div><br>



> In the [first tutorial](https://www.rabbitmq.com/tutorials/tutorial-one-java.html) we wrote programs to send and receive messages from a named queue. In this one we'll create a *Work Queue* that will be used to distribute time-consuming tasks among multiple workers.
>
> The main idea behind Work Queues (aka: *Task Queues*) is to avoid doing a resource-intensive task immediately and having to wait for it to complete. Instead we schedule the task to be done later. We encapsulate a *task* as a message and send it to a queue. A worker process running in the background will pop the tasks and eventually execute the job. When you run many workers the tasks will be shared between them.
>
> This concept is especially useful in web applications where it's impossible to handle a complex task during a short HTTP request window.



**Publisher.java**

```java
/**
 * Publisher of work queues
 */
public class Publisher {

    static final String WORK_QUEUE_NAME = "work_queue";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = ConnectionFactoryUtil.getConnectionFactory();

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()
        ) {
            channel.queueDeclare(WORK_QUEUE_NAME, true, false, false, null);

            StringBuilder s = new StringBuilder("Work queue here!");

            // simulate
            for (int i = 0; i < 10; i++) {
                s.append(i);
                channel.basicPublish("", WORK_QUEUE_NAME, null, s.toString().getBytes());
            }

            System.out.println("Message sent!");
        }

    }

}
```



创建 2 个消费者

**Consumer1.java**

```java
/**
 * Consumer1 of work queues
 */
public class Consumer1 {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = ConnectionFactoryUtil.getConnectionFactory();
        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(Publisher.WORK_QUEUE_NAME, true, false, false, null);
        System.out.println("Receiving message");

        // request a specific prefetchCount "quality of service" settings for this channel.
        channel.basicQos(1);

        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                System.out.println("Consumer1 here!");

                try {
                    String receivedMessage = new String(message.getBody());
                    System.out.println(receivedMessage);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    System.out.println("Done");

                    /*
                     * In order to make sure a message is never lost, RabbitMQ supports message acknowledgments.
                     * An acknowledgement is sent back by the consumer to tell RabbitMQ that a particular message has been received,
                     * processed and that RabbitMQ is free to delete it.
                     * */
                    channel.basicAck(message.getEnvelope().getDeliveryTag(), false);

                }
            }
        };

        CancelCallback cancelCallback = new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {
                System.out.println("Receive failed!");
            }
        };

        channel.basicConsume(Publisher.WORK_QUEUE_NAME, deliverCallback, cancelCallback);

    }
}
```



**Consumer2.java**

```java
/**
 * Consumer2 of work queues
 */
public class Consumer2 {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = ConnectionFactoryUtil.getConnectionFactory();
        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(Publisher.WORK_QUEUE_NAME, true, false, false, null);
        System.out.println("Receiving message");

        // request a specific prefetchCount "quality of service" settings for this channel.
        channel.basicQos(1);

        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                System.out.println("Consumer2 here!");

                try {
                    String receivedMessage = new String(message.getBody());
                    System.out.println(receivedMessage);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    System.out.println("Done");

                    /*
                     * In order to make sure a message is never lost, RabbitMQ supports message acknowledgments.
                     * An acknowledgement is sent back by the consumer to tell RabbitMQ that a particular message has been received,
                     * processed and that RabbitMQ is free to delete it.
                     * */
                    channel.basicAck(message.getEnvelope().getDeliveryTag(), false);

                }
            }
        };

        CancelCallback cancelCallback = new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {
                System.out.println("Receive failed!");
            }
        };

        channel.basicConsume(Publisher.WORK_QUEUE_NAME, deliverCallback, cancelCallback);

    }
}
```



启动生产者

<div align="center"> <img src="image-20200811150435767.png" width="40%"/> </div><br>





启动消费者 1

<div align="center"> <img src="image-20200811192352827.png" width="30%"/> </div><br> 





启动消费者 2

<div align="center"> <img src="image-20200811192409318.png" width="30%"/> </div><br>





## 9. Publish / Subscribe

**什么是 publish / subscribe ?**





<div align="center"> <img src="wx.jpg" width="40%"/> </div><br>



举个微信公众号的例子：



作者相当于生产者，产出文章

微信公众号平台相当于交换机（稍后会介绍）

用户相当于消费者，消费文章



作者通过微信公众号生产文章，而所有订阅的用户都可以收到推送的文章，都可以消费



`RabbitMQ` 中的 `publish / subscribe` 也是这个原理







<div align="center"> <img src="image-20200811192620816.png" width="50%"/> </div><br>

> In the [previous tutorial](https://www.rabbitmq.com/tutorials/tutorial-two-java.html) we created a work queue. The assumption behind a work queue is that each task is delivered to exactly one worker. In this part we'll do something completely different -- we'll deliver a message to multiple consumers. This pattern is known as "publish/subscribe".
>
> To illustrate the pattern, we're going to build a simple logging system. It will consist of two programs -- the first will emit log messages and the second will receive and print them.
>
> In our logging system every running copy of the receiver program will get the messages. That way we'll be able to run one receiver and direct the logs to disk; and at the same time we'll be able to run another receiver and see the logs on the screen.
>
> **Essentially, published log messages are going to be broadcast to all the receivers.**



`publish / subscribe` 的思想很简单：**生产者发送消息，多个消费者都能收到消息**



这里引入一个新的概念：`exchange`



`exchange` 的功能：

- 接受来自 `publisher` 的消息
- 将消息分发到不同的队列中



**exchange 如何将消息分发到不同的队列？**

- direct
- topic
- headers
- fanout



**BuiltinExchangeType.java**

```java
package com.rabbitmq.client;

/**
 * Enum for built-in exchange types.
 */
public enum BuiltinExchangeType {

    DIRECT("direct"), FANOUT("fanout"), TOPIC("topic"), HEADERS("headers");

    private final String type;

    BuiltinExchangeType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
```



首先，我们先讨论 `fanout`


<div align="center"> <img src="image-20200812104252944.png" width="50%"/> </div><br>

`fanout` 作为 `exchange` 的一种模式，核心就是广播

对于其接收到的消息，都将转发到所有"认识"的队列



<div align="center"> <img src="image-20200812105831210.png" width="60%"/> </div><br>



先定义两个消费者，测试 `fanout` 广播功能

**Consumer1.java**

```java
/**
 * Consumer1 of publish & subscribe
 */
public class Consumer1 {

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = ConnectionFactoryUtil.getConnectionFactory();

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        channel.queueDeclare(QUEUE_NAME1, true, false, true, null);

        channel.queueBind(QUEUE_NAME1, EXCHANGE_NAME, "");

        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                System.out.println("Consumer1 have received message: " + new String(message.getBody()));
            }
        };

        channel.basicConsume(QUEUE_NAME1, deliverCallback, (CancelCallback) null);

    }

}
```



**Consumer2.java**

```java
/**
 * Consumer2 of publish and subscribe
 */
public class Consumer2 {

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = ConnectionFactoryUtil.getConnectionFactory();

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        channel.queueDeclare(QUEUE_NAME2, true, false, true, null);

        channel.queueBind(QUEUE_NAME2, EXCHANGE_NAME, "");

        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                System.out.println("Consumer2 have received message: " + new String(message.getBody()));
            }
        };

        channel.basicConsume(QUEUE_NAME2, deliverCallback, (CancelCallback) null);
        
    }

}
```



**Publisher.java**

```java
/**
 * Publisher of public & subscribe
 */
public class Publisher {

    static final String EXCHANGE_NAME = "exchange_fanout";
    static final String QUEUE_NAME1 = "exchange_fanout_queue1";
    static final String QUEUE_NAME2 = "exchange_fanout_queue2";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = ConnectionFactoryUtil.getConnectionFactory();

        try (
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel();
        ) {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

            channel.queueDeclare(QUEUE_NAME1, true, false, true, null);
            channel.queueDeclare(QUEUE_NAME2, true, false, true, null);

            channel.queueBind(QUEUE_NAME1, EXCHANGE_NAME, "");
            channel.queueBind(QUEUE_NAME2, EXCHANGE_NAME, "");

            String message = "EXCHANGE FANOUT here!";
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());

        }

    }

}
```



先启动 `consumer` 开始监听，再启动 `publisher` 生产消息

<div align="center"> <img src="image-20200812160546278.png" width="50%"/> </div><br>

<div align="center"> <img src="image-20200812160559050.png" width="80%"/> </div><br>



测试发布消息

<div align="center"> <img src="image-20200812160749392.png" width="80%"/> </div><br>







消费成功！

<div align="center"> <img src="image-20200812160823265.png" width="50%"/> </div><br>







<div align="center"> <img src="image-20200812160833504.png" width="50%"/> </div><br>









## 10. Routing

















## 11. Topics

















## Conclusion

- 看官网教程可以学习到最新的技术 / 知识，例如这次的 `Try-with-resources`
- 理解后消化，以简洁的语言写出来，而不是照搬教程
- 好的代码就相当于注释





## 参考资料

- [RabbitMQ Tutorials](https://www.rabbitmq.com/getstarted.html)
- [什么是消息队列？](https://juejin.im/post/6844903817348136968)
- [消息队列的使用场景是怎样的？](https://www.zhihu.com/question/34243607)
- [消息队列设计精要](https://tech.meituan.com/2016/07/01/mq-design.html)
- [Java – Try with Resources](https://www.baeldung.com/java-try-with-resources)











