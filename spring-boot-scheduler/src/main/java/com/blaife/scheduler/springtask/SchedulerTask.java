package com.blaife.scheduler.springtask;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 调度任务实现类
 */
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
