# 定时器介绍：
java开发中常用到的定时器有Timer、ScheduledExecutorService、SpringTask、Quartz。

# SpringTask：
SpringTask不需要额外引入依赖，Spring默认集成

# 编写定时任务类：
```java
package com.blaife.timetask;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 定时任务1
 */
@Component
public class SchedulerTask {

    /**
     * 固定时间执行
     *
     */
    @Scheduled(cron="0 */1 * * * * ")
    private void process(){
        System.out.println("process：" + LocalDateTime.now());
    }

    /**
     * 固定倒计时执行
     * 六秒一次
     */
    @Scheduled(fixedRate = 6000)
    public void reportCurrentTime() {
        System.out.println("现在时间：" + LocalDateTime.now());
    }
}
```
注意需要使用@EnableScheduling注解，通常用在配置类上面,表示开启定时任务
该注解会使Spring 容器中所有bean中的@Scheduled注解起作用。

# 使用@Async实现异步调用
在配置类中加入注解@EnableAsync开启异步调用，在定时方法上加入注解@Async使方法异步化。
可以使用Thread.sleep()进行测试异步是否可行

# @Scheduled注解内容详解：
1. cron cron表达式
2. fixedRate 启动后下次启动时间间隔
3. fixedDelay 启动结束后下次启动时间间隔
4. initialDelay fixedRate|fixedDelay第一次执行前延迟的毫秒数

# cron表达式详解：
按照顺序时：
second(秒)， minute(分)， hour(时),day of month(日),month(月),day of week(周几)  
字段与值表格入下：
|字段     |允许值               |允许的特殊符号   |
|:--------|:------------------:|:--------------|
|秒       |0-59                 |,-*/           |
|分       |0-59                 |,-*/           |
|小时     |0-23                  |,-*/          |
|日期     |1-31                  |,-*?/LWC      |
|月份     |1-12                  |,-*/          |
|星期     |0-7或SUN-SAT 0 7是SUN	|,-*?/LC#       |

特殊符号释义如下：
|符号     |释义                          |
|:------:|:-----------------------------|
|`,`     |枚举                           |
|`-`     |区间                           |
|`*`     |任意                           |
|`/`     |步长                           |
|`?`     |日/星期冲突匹配                 |
|`L`     |最后                           |
|`W`     |工作日                         |
|`C`     |和Calendar联系后计算过的值      |
|`#`     |星期 4#2 第2个星期四            |

# cron表达式实例如下：
0 0 0 * * * -- 每天零时执行一次
0 0/15 14,18 * * ? -- 每天14点整和18点整，每隔15分钟执行一次
0 15 10 ? * 1-6 -- 每个月的周一到周六 10:15分执行一次
0 0 2 ? * 6L -- 每个月的最后一个周六凌晨2点执行一次 
0 0 2 LW * ? -- 每个月的最后一个工作日凌晨2点执行一次
0 0 2-4 ? * 1#1 -- 每个月的第一个周一凌晨2点到4点期间，每个整点都执行一次