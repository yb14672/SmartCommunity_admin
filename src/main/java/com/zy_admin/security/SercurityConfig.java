package com.zy_admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class SercurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private LoginFailurHandler loginFailurHandler;

    @Autowired
    private MyUserDetailsServiceImpl myUserDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private LoginSuccessLogOutHandler loginSuccessLogOutHandler;
    @Autowired
    private MyAccessDenied myAccessDenied;

    private static final String[] URL_WHITELIST = {
            "/sysUser/login",
            "/sysUser/logout",
//            "/user/profile",
            "/sysUser/logout",
            "sysMenu/getMenus",
            "sysUser/getAvatarById",
            "localhost:8080/druid"
    };

    /**
     * JWT认证
     *
     * @return
     * @throws Exception
     */
    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager());
        return jwtAuthenticationFilter;
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //开启跨域 crsf攻击关闭
        http
                .headers()
                .frameOptions()
                .disable()
                .and()
                .cors()
                .and()
                .csrf().disable()

                //登录登出配置
                .formLogin()
                .loginProcessingUrl("/sysUser/login")
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailurHandler)
                .and()
                .logout().logoutSuccessHandler(loginSuccessLogOutHandler)


                //session禁用配置
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                //拦截配置
                .and()
                .authorizeRequests()
                .antMatchers(URL_WHITELIST).permitAll()//白名单
                .anyRequest()
                .authenticated()


                //异常处理
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(myAccessDenied)
                //自定配置
                .and()
                .addFilter(jwtAuthenticationFilter());

        //无权访问
    }
}
