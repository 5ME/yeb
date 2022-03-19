package com.ygq.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger2Config
 *
 * @author Yin Guiqing
 * @version 1.0
 * @date 2022-02-28 18:42
 */
@EnableSwagger2
@Configuration
public class Swagger2Config {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ygq.server" +
                        ".controller"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemes());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("云E办接口文档")
                .description("基于 Spring boot + Vue 的前后端分离的")
                .contact(new Contact("YGQ", "https://github.com/5ME",
                        "cplasfyin@163.com"))
                .termsOfServiceUrl("")
                .version("1.0.0")
                .build();
    }

    private List<ApiKey> securitySchemes() {
        // 设置请求头信息
        List<ApiKey> res = new ArrayList<>();
        ApiKey apiKey = new ApiKey("Authorization", "Authorization", "Header");
        res.add(apiKey);
        return res;
    }

    private List<SecurityContext> securityContexts() {
        //设置需要登录认证的路径
        List<SecurityContext> res = new ArrayList<>();
        res.add(getContextPath("/hello/.*"));
        return res;
    }

    private SecurityContext getContextPath(String pathRegex) {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(pathRegex))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> res = new ArrayList<>();
        AuthorizationScope authorizationScope = new AuthorizationScope("global",
                "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        res.add(new SecurityReference("Authorization", authorizationScopes));
        return res;
    }
}
