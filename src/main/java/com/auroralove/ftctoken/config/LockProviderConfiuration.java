package com.auroralove.ftctoken.config;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 分布式定时任务锁
 *
 * @author zyu
 * @date 2019/3/4
 */
@Configuration
public class LockProviderConfiuration {

    @Autowired
    DataSource dataSource;

    @Bean
    public LockProvider lockProvider () {
        return new JdbcTemplateLockProvider(dataSource);
    }
}
