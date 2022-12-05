package com.zy_admin.security;


import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.sys.service.SysUserService;
import com.zy_admin.util.JwtUtil;
import com.zy_admin.util.RequestUtil;
import com.zy_admin.util.StringUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * jwt身份验证过滤器
 *
 * @author 张友炜
 * @date 2022/12/04
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
    @Autowired
    RequestUtil requestUtil;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    private MyUserDetailsServiceImpl myUserDetailsService;
    private static final String[] URL_WHITELIST = {
            "/sysUser/login",
            "/sysUser/logout",

    };


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader("token");
        System.out.println("请求url"+request.getRequestURI());
        //如果token是空 或者url在白名单里则放行
        if (StringUtil.isEmpty(token) || new ArrayList<String>(Arrays.asList(URL_WHITELIST)).contains(request.getRequestURI())){
            chain.doFilter(request,response);
            return;
        }

        if (!JwtUtil.checkToken(token))
        {
            throw new Exception("token无效或者过期");
        }
        SysUser user = requestUtil.getUser(request);
        String username =user.getUserName();
        SysUser sysUser = sysUserService.getByUsername(username);


        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,null,myUserDetailsService.getUserAuthority(sysUser.getUserId()));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        chain.doFilter(request,response);
    }
}
