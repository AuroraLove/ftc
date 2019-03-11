package com.auroralove.ftctoken.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.*;


/**
 * 定时任务配置器
 *
 * @author zyu
 * @date 2019/3/9
 */
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(setTaskExecutors());
    }

    @Bean(name = "taskScheduler")
    public Executor setTaskExecutors(){
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-Scheduled-runner-%d").build();
        ExecutorService executorService = new ThreadPoolExecutor(5,10,100L,
                TimeUnit.MILLISECONDS,new LinkedBlockingQueue<>(),namedThreadFactory);
        return executorService;
    }
}
