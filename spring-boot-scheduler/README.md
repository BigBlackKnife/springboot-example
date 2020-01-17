# SpringTask
目前java开发中常用到的定时器有SpringTask, Timer, Quartz等
- Timer：Java自带的java.util.Timer类，它只能让程序按照某一个频度执行，但不能在指定时间运行。一般用的较少。
- Quartz：Quartz是一个功能抢到的调度器，可以使程序在指定时间执行，也可以按照某一个频度执行，配置起来稍显复杂。
- SpringTask：spring3.0之后自带的task，可以看作一个轻量级的Quartz, 使用也是非常方便的。

## 实例使用
SpringTask是Spring默认集成的，不需要额外引入依赖。
在使用之前我们需要在springboot启动类中使用注解`@EnableScheduling`开启任务调度、
使用注解`@EnableAsync`开启异步编程。

### 编写定时任务类
```java
@Component
public class SchedulerTask {

    /**
     * 固定时间执行
     * 一分钟执行一次
     */
    @Scheduled(cron="0/5 * * * * * ")
    @Async
    public void process() {
        try {
            System.out.println("休眠开始");
            Thread.sleep(3000);
            System.out.println("休眠结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("process：" + LocalDateTime.now());
    }

    /**
     * 固定倒计时执行
     * 六秒一次
     */
    @Scheduled(fixedRate = 6000)
    @Async
    public void reportCurrentTime() {
        System.out.println("现在时间：" + LocalDateTime.now());
    }
}
```
在方法上使用注解`@Scheduled`配置执行规则，使用注解`@Async`使方法异步化。

### `@Scheduled`注解的参数解释
1. cron：cron表达式，具体使用方法见下方。
2. fixedRate：启动后下次启动时间间隔
3. fixedDelay：启动结束后下次启动时间间隔
4. initialDelay：fixedRate|fixedDelay第一次执行前延迟的毫秒数

请注意fixedRate和fixedDelay参数的区别，一个在方法开始执行时计时，一个在方法执行结束后计时（一个方法的执行也需要时间）。

### cron表达式详解
字符顺序为：second(秒)， minute(分)， hour(时),day of month(日),month(月),day of week(周几)  
字段可取值如下表格：
|字段            |允许值                    |允许的特殊符号   |
|:---------------|:-----------------------:|:---------------|
|秒              |0-59                     |,-*/            |
|分              |0-59                     |,-*/            |
|小时            |0-23                     |,-*/             |
|日期            |1-31                     |,-*?/LWC         |
|月份            |1-12                     |,-*/             |
|星期            |0-7或SUN-SAT 0 7是SUN     |,-*?/LC#        |

特殊符号释义：
|符号             |释义                           |
|:---------------:|:------------------------------|
|`,`              |枚举                           |
|`-`              |区间                           |
|`*`              |任意                           |
|`/`              |步长                           |
|`?`              |日/星期冲突匹配                 |
|`L`              |最后                           |
|`W`              |工作日                         |
|`C`              |和Calendar联系后计算过的值      |
|`#`              |星期 4#2 第2个星期四            |

### cron表达式实例如下
0 0 0 * * * -- 每天零时执行一次
0 0/15 14,18 * * ? -- 每天14点整和18点整，每隔15分钟执行一次
0 15 10 ? * 1-6 -- 每个月的周一到周六 10:15分执行一次
0 0 2 ? * 6L -- 每个月的最后一个周六凌晨2点执行一次 
0 0 2 LW * ? -- 每个月的最后一个工作日凌晨2点执行一次
0 0 2-4 ? * 1#1 -- 每个月的第一个周一凌晨2点到4点期间，每个整点都执行一次