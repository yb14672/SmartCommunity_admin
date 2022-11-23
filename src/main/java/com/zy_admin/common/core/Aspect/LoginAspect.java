package com.zy_admin.common.core.Aspect;

import com.zy_admin.sys.entity.SysLogininfor;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.sys.service.SysLogininforService;
import com.zy_admin.util.IpUtil;
import com.zy_admin.common.core.Result.Result;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

/**
 * @author fangqian
 */
@Component
@Aspect
public class LoginAspect {
    @Resource
    private SysLogininforService sysLogininforService;

    /**
     * 切入点表达式
     */
    @Pointcut("execution(* com.zy_admin.sys.controller.SysUserController.login(..))")
    public void loginPointcut() {
    }

    /**
     * 后置返回
     *
     * @param joinPoint
     */
    @AfterReturning(value = "loginPointcut()", returning = "obj")
    public void afterReturning(JoinPoint joinPoint, Object obj) {
        SysLogininfor sysLogininfor = new SysLogininfor();
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);
        //获取参数
        SysUser user = (SysUser)joinPoint.getArgs()[0];
        //获取登录Ip
        String ip = IpUtil.getIpAddress(request);
        String pattern = "^(127\\.0\\.0\\.1)|(localhost)|(10\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})|(172\\.((1[6-9])|(2\\d)|(3[01]))\\.\\d{1,3}\\.\\d{1,3})|(192\\.168\\.\\d{1,3}\\.\\d{1,3})$";
        if (Pattern.matches(pattern,ip)){
            sysLogininfor.setLoginLocation("内网Ip");
        }else{
            sysLogininfor.setLoginLocation("外网Ip");
        }
        //在web应用中我们通过request获取用户的Agent:
        String agent=request.getHeader("User-Agent");
        //解析agent字符串
        UserAgent userAgent = UserAgent.parseUserAgentString(agent);
        //获取浏览器对象
        Browser browser = userAgent.getBrowser();
        //获取操作系统对象
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        //设置参数
        //登录账号
        sysLogininfor.setUserName(user.getUserName());
        //登录IP
        sysLogininfor.setIpaddr(ip);
        //浏览器名称
        sysLogininfor.setBrowser(browser.getName());
        //操作系统名称
        sysLogininfor.setOs(operatingSystem.getName());
        //登录时间
        sysLogininfor.setLoginTime(LocalDateTime.now().toString());
        Result result = (Result) obj;
        //登录状态
        if (result.getMeta().getErrorCode() == 2015){
            sysLogininfor.setStatus("0");
        }else{
            sysLogininfor.setStatus("1");
        }
        //返回信息
        sysLogininfor.setMsg(result.getMeta().getErrorMsg());

        sysLogininforService.getBaseMapper().insert(sysLogininfor);
    }
}
