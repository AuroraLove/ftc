package com.auroralove.ftctoken;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
