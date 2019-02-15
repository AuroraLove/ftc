package com.auroralove.ftctoken;

import com.auroralove.ftctoken.utils.SendSMSUitl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FTCApplicationTests {

    @Test
    public void contextLoads() {
//        JPushInstance.SendPush("test");
        //15170768917 17538139836
        SendSMSUitl.sendSMS("17538139836",123456);
    }

}
