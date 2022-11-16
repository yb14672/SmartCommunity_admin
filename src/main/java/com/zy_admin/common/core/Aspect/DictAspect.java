package com.zy_admin.common.core.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 字典aop
 *
 * @author yb14672
 * Time:2022/11/12 - 14:35
 **/
@Component
@Aspect
public class DictAspect {
    /**
     * 切入点表达式
     */
    @Pointcut("execution(* com.zy_admin.sys.controller.SysDictTypeController.*(..))")
    public void pc() {
    }

    /**
     * 前置通知
     *
     * @param joinPoint
     */
    @Before(value = "pc()")
    public void before(JoinPoint joinPoint) {
        String name = joinPoint.getSignature().getName();
        System.out.println("进入" + name + "方法");
    }

    /**
     * 后置通知
     *
     * @param joinPoint
     */
    @After(value = "pc()")
    public void after(JoinPoint joinPoint) {
        String name = joinPoint.getSignature().getName();
        System.out.println(name + "方法结束");
    }

    /**
     * 返回通知
     *
     * @param joinPoint
     * @param result
     */
    @AfterReturning(value = "pc()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        String name = joinPoint.getSignature().getName();
        System.out.println(name + "方法返回通知，返回值为：" + result);
    }

    /**
     * 异常通知
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(value = "pc()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Exception e) {
        String name = joinPoint.getSignature().getName();
        System.out.println(name + "方法出现异常，异常信息为：" + e.getMessage());
    }
}
