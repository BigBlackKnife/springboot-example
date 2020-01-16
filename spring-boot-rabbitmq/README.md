# RabbitMQ 消息队列
> 消息中间件最主要的作用是解耦，中间件最标准的用法是生产者生产消息传送到队列，消费者从队列中拿取消息并处理，生产者不用关心是谁来消费，消费者不用关心谁在生产消息，从而达到解耦的目的。在分布式的系统中，消息队列也会被用在很多其它的方面，比如：分布式事务的支持，RPC 的调用等等。

## Rabbit 简介
> RabbitMQ是实现了高级消息队列协议（AMQP）的开源消息代理软件（亦称面向消息的中间件）。RabbitMQ服务器是用Erlang语言编写的，而集群和故障转移是构建在开放电信平台框架上的。所有主要的编程语言均有与代理接口通讯的客户端库。-- 百度百科

### AMQP
![AMQP](https://github.com/BigBlackKnife/spring-boot-examples/tree/master/spring-boot-rabbitmq/src/main/resources/img/AMQP.png)
- 大体分为3部分 Producer (消息生产者)、 Consumner (消息消费者)、 Broker (服务端)。
- Broker中包含有完整的Virtual Host (虚拟主机)、 Exchange(交换机)、 Queue（队列)。
- 一个Broker可以创建多个Virtual Host，注意，如果AMQP是由多个Broker构成的集群提供服务， 那么一个Virtual Host也可以由多个Broker共同构成。
- Exchange和可以绑定多个Queue也可以同时绑定其他Exchange。消息通过Exchange时， 会按照Exchange中设置的Routing（路由）规则，将消息发送到符合的Queue或者Exchange中。
- Connection是由Producer（消息生产者）和Consumer（消息消费者）创建的连接，连接到Broker物理节点上。 但是有了Connection后客户端还不能和服务器通信，
在Connection之上客户端会创建Channel，连接到Virtual Host或者Queue上，这样客户端才能向Exchange发送消息或者从Queue接受消息。一个Connection上允许存在多个Channel，只有Channel中能够发送/接受消息。

### Rabbit 路由方式
- Direct Pattern： 先匹配, 再投送. 即在绑定时设定一个routing_key, 消息的routing_key匹配时, 才会被交换器投送到绑定的队列中去.(不需要写交换机默认指向队列即可)
- Fanout Pattern： 类似于广播一样，将消息发送给所有和他绑定的队列
- Topic Pattern： 按规则转发消息 #：表示零个或多个单词。*：表示一个单词（最灵活）
- Header Pattern： 设置 header attribute参数类型的交换机

## Rabbit 使用
### 引入依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

### 配置文件
以Ranout为例，其他具体配置请看代码
```java
@Configuration
public class RabbitConfig {
    /**
     * 交换机名 Fanout Exchange
     */
    public static final String FANOUT_EXCHANGE = "fanout.exchange";

    /**
     * 队列名 Fanout Queue 1
     */
    public static final String FANOUT_QUEUE_ONE = "fanout.queue.one";

    /**
     * 队列名 Fanout Queue 2
     */
    public static final String FANOUT_QUEUE_TWO = "fanout.queue.two";

    /**
     * 创建交换机
     * Fanout Exchange
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    /**
     * 创建队列
     * Fanout Queue 1
     * @return
     */
    @Bean
    public Queue fanoutQueueOne() {
        return new Queue(FANOUT_QUEUE_ONE, true);
    }

    /**
     * 将队列(Fanout Queue 1)绑定到交换机(Fanout Exchange)上
     * @return
     */
    @Bean
    public Binding fanoutBindingOne() {
        return BindingBuilder.bind(fanoutQueueOne()).to(fanoutExchange());
    }

    /**
     * 创建队列
     * Fanout Queue 2
     * @return
     */
    @Bean
    public Queue fanoutQueueTwo() {
        return new Queue(FANOUT_QUEUE_TWO, true);
    }

    /**
     * 将队列(Fanout Queue 2)绑定到交换机(Fanout Exchange)上
     * @return
     */
    @Bean
    public Binding fanoutBindingTwo() {
        return BindingBuilder.bind(fanoutQueueTwo()).to(fanoutExchange());
    }

}
```

### 发送者（消息生产者）
```java
@Component
public class RabbitSender {

    // slf4j 日志
    private static final Logger log = LoggerFactory.getLogger(RabbitSender.class);

    // Rabbit模板对象
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendFanout(String msg) {
        log.info("this is fanout send: " + msg);
        amqpTemplate.convertAndSend(RabbitConfig.FANOUT_EXCHANGE, "", msg);
    }
}
```

### 接收者（消息消费者）
```java
@Component
public class RabbitReceiver {

    // slf4j 日志
    private static final Logger log = LoggerFactory.getLogger(RabbitReceiver.class);

    @RabbitListener(queues = RabbitConfig.FANOUT_QUEUE_ONE)
    public void receiveFanoutOne(String msg) {
        log.info("this is fanout1 receive: " + msg);
    }

    @RabbitListener(queues = RabbitConfig.FANOUT_QUEUE_TWO)
    public void receiveFanoutTwo(String msg) {
        log.info("this is fanout2 receive: " + msg);
    }
}
```

### 测试文件
```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitTest {

    @Autowired
    private RabbitSender sender;

    /**
     * fanout 测试发送5条 消费者共收到10条 （匹配到两个队列）
     */
    @Test
    public void fanoutTest() {
        for (int i = 0; i < 5; i++) {
            sender.sendFanout("test fanout for " + i);
        }
    }
}
```