package com.auroralove.ftctoken.init;

import com.auroralove.ftctoken.service.DealService;
import com.auroralove.ftctoken.service.GetOrderThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 匹配方法启动初始化线程
 *
 * @author zyu
 * @date 2019/3/6
 */
@Component
@Order(value = 1)
public class InitRunner implements ApplicationRunner {

    @Autowired
    private DealService dealService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        int n = dealService.initDealMatchingOrder();
    }
}
