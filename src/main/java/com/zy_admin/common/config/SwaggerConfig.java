package com.zy_admin.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
 * @author yb14672
 * Time:2022/11/22 - 9:20
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static ApiInfo DEFAULT = null;

    /** 是否开启swagger */
    @Value("${swagger.enabled}")
    private boolean enabled;
    @Bean
    public Docket createSystemApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("系统相关接口")
                // 是否启用Swagger
                .enable(enabled)
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(apiInfo())
                // 设置哪些接口暴露给Swagger展示
                .select()
                // 扫描所有有注解的api，用这种方式更灵活
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 扫描指定包中的swagger注解
                 .apis(RequestHandlerSelectors.basePackage("com.zy_admin.sys.controller"))
                // 扫描所有 .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(setHeaderToken());
                /* 设置安全模式，swagger可以设置访问token */
//                .securitySchemes(securitySchemes())
//                .securityContexts(securityContexts())
    }
    @Bean
    public Docket createCommunityApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("社区相关接口")
                // 是否启用Swagger
                .enable(enabled)
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(apiInfo())
                // 设置哪些接口暴露给Swagger展示
                .select()
                // 扫描指定包中的swagger注解
                 .apis(RequestHandlerSelectors.basePackage("com.zy_admin.community.controller"))
                // 扫描所有 .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(setHeaderToken());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("智慧社区接口文档")
                .description("描述：用于社区管理系统的接口对接")
                .version("1.0")
                .build();
    }

    /**
     *设置请求头
     */
    private List<Parameter> setHeaderToken() {
        List<Parameter> pars = new ArrayList<>();
        ParameterBuilder userId = new ParameterBuilder();
        userId.name("token").description("用户TOKEN").modelRef(new ModelRef("string")).parameterType("header")
                .required(true).build();
        pars.add(userId.build());
        return pars;
    }
}
