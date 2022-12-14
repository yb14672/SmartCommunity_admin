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
            "/user/profile",
            "sysMenu/getMenus",
            "sysUser/getAvatarById",
            "/zyOwner/login",
            "/zyOwner/getOwner",
            "/tool/swagger",
            "/tool/swagger",
            "/**/swagger-ui.html",
            "/**/doc.html",
            "/**/doc.html#/**",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/zyOwnerRoom/getTree",
            "/zyOwnerRoom/selectOwnerRoomByOwnerId",
            "/zyOwnerPark/selectNoBindingAndStatusPark",
            "/zyOwnerPark/queryOwnerParkRecord",
            "/zyComplaintSuggest/insertSuggest",
            "/zyOwnerRoom/insert",
            "/zyOwnerRoom/getOwnerRoomByOwnerId",
            "/mini/interaction/**",
            "/mini/comment/**",
            "/zyRepair/insertRepair",
            "/zyVisitor/insertVisitor",
            "/zyVisitor/queryVisitorRecord",
            "/zyRepair/getRepairByOwnerId",
            "/upload",
            "/**/druid/index.html",
            "/**/druid/**",
    };

    /**
     * JWT??????
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
        //???????????? crsf????????????
        http
                .headers()
                .frameOptions()
                .disable()
                .and()
                .cors()
                .and()
                .csrf().disable()

                //??????????????????
                .formLogin()
                .loginProcessingUrl("/sysUser/login")
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailurHandler)
                .and()
                .logout().logoutSuccessHandler(loginSuccessLogOutHandler)


                //session????????????
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                //????????????
                .and()
                .authorizeRequests()
                .antMatchers(URL_WHITELIST).permitAll()//?????????
                .anyRequest()
                .authenticated()


                //????????????
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(myAccessDenied)
                //????????????
                .and()
                .addFilter(jwtAuthenticationFilter());

                //????????????
//
    }
}
