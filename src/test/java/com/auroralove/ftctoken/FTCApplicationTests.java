package com.auroralove.ftctoken;

import com.auroralove.ftctoken.utils.CanlendarUtil;
import com.auroralove.ftctoken.utils.SendSMSUitl;
import net.javacrumbs.shedlock.core.SchedulerLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
@Component
public class FTCApplicationTests {

    private static final String SIXTY_MIN = "PT60M";
    private static final String THREE_MIN = "PT3M";

    @Value("${system.startTime}")
    private String startTime;

    @Value("${system.endTime}")
    private String endTime;

    @Test
    public void contextLoads() {
//        JPushInstance.SendPush("test");
        //15170768917 17538139836
        SendSMSUitl.sendSMS("17538139836",123456);
    }

    @Scheduled(cron = "0/5 * * * * ? ")
    @SchedulerLock(name = "test",lockAtLeastForString = THREE_MIN,lockAtMostForString = SIXTY_MIN)
    public void test() {
        System.out.println(LocalDateTime.now());
        System.out.println(Thread.currentThread().getName()+"--executed......");
    }

    @Test
    public void dateTest(){
        boolean effectiveDate = CanlendarUtil.isEffectiveDate(startTime, endTime);
        if (CanlendarUtil.isEffectiveDate(startTime,endTime)){
            System.out.println("11111");
        }
        System.out.println(effectiveDate);
    }

    @Test
    public void equalTest(){
        Long a1 = 119L;
        Long a2 = 119L;
        boolean effectiveDate = a1.equals(a2);
        System.out.println(effectiveDate);
    }

}
