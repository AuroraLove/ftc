package com.auroralove.ftctoken;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zyu
 * @date 2019-1-22 20:52
 */
@SpringBootApplication
@MapperScan("com.auroralove.ftctoken.mapper")
public class FTCApplication {

    public static void main(String[] args) {
        SpringApplication.run(FTCApplication.class, args);
    }


}
