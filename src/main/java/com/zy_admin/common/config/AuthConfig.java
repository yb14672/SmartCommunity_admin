package com.zy_admin.common.config;

import com.zy_admin.common.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置拦截器
 *
 * @author admin
 */
@Configuration
public class AuthConfig implements WebMvcConfigurer {
    @Bean
    public AuthInterceptor initAuthInterceptor() {
        return new AuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //addPathPatterns：哪些路径下的会被拦截
        //excludePathPatterns：哪些路径下不会被拦截
        registry.addInterceptor(initAuthInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/**/login",
                        "/**/register",
                        "/upload",
                        "/tool/swagger",
                        "/**/swagger-ui.html",
                        "/**/doc.html",
                        "/**/doc.html#/**",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/**/druid/**"
                );
    }
}
