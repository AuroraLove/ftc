package com.auroralove.ftctoken;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zyu
 * @date 2019-1-22 20:52
 */
@SpringBootApplication
@Configuration
@EnableSwagger2
@EnableScheduling//启动定时任务
@EnableTransactionManagement
@MapperScan("com.auroralove.ftctoken.mapper")
@ComponentScan(basePackages = {"com.auroralove.ftctoken.*"})
public class FTCApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(FTCApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(FTCApplication.class, args);
    }


    @Bean
    public Docket createRestApi() {
        ParameterBuilder token = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        token.name("token").description("user token")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        pars.add(token.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.auroralove.ftctoken.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("mars组件 RESTful APIs")
                .description("mars组件api接口文档")
                .version("1.0")
                .build();
    }
}
