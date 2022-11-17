package com.zy_admin.common.core.Aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zy_admin.common.core.annotation.MyLog;
import com.zy_admin.sys.entity.SysDept;
import com.zy_admin.sys.entity.SysOperLog;
import com.zy_admin.sys.entity.SysUser;
import com.zy_admin.sys.service.SysDeptService;
import com.zy_admin.sys.service.SysOperLogService;
import com.zy_admin.util.IpUtils;
import com.zy_admin.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author yb14672
 */
@Aspect
@Component
@Slf4j
public class LogAspect {
    /**
     * 该Service及其实现类相关代码请自行实现，只是一个简单的插入数据库操作
     */
    @Autowired
    private SysOperLogService sysOperLogService;
    @Autowired
    private SysDeptService sysDeptService;
    @Resource
    private RequestUtil requestUtil;


    /**
     * @annotation(MyLog类的路径) 在idea中，右键自定义的MyLog类-> 点击Copy Reference
     */
    @Pointcut("@annotation(com.zy_admin.common.core.annotation.MyLog)")
    public void logPointCut() {
        log.info("------>配置切入点");
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint,Object result) throws IllegalAccessException {
        handleLog(joinPoint, null,result);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
//    @AfterThrowing(value = "logPointCut()", throwing = "e")
//    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
//        handleLog(joinPoint, e,null);
//    }

    private void handleLog(final JoinPoint joinPoint, final Exception e,Object result) {
        // 获得MyLog注解
        MyLog controllerLog = getAnnotationLog(joinPoint);
        if (controllerLog == null) {
            return;
        }
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

        SysOperLog operLog = new SysOperLog();
        //给日志赋值
        // 操作状态（0正常 1异常）
        operLog.setStatus(0);
        //方法
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        operLog.setMethod(className + "." + methodName + "()");
        //请求方法
        operLog.setRequestMethod( request.getMethod() );
        //获取昵称
        SysUser user = requestUtil.getUser(request);
        //获取名字
        operLog.setOperName(user.getUserName());
        //获取部门
        SysDept deptById = sysDeptService.getDeptById(user.getDeptId());
        operLog.setDeptName(deptById.getDeptName());
        // 获取URI
        operLog.setOperUrl(request.getRequestURI());
        //获取ip
        String realIp = IpUtils.getIpAddress(request);
        operLog.setOperIp(realIp);
        //操作地点
        String pattern = "^(127\\.0\\.0\\.1)|(localhost)|(10\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})|(172\\.((1[6-9])|(2\\d)|(3[01]))\\.\\d{1,3}\\.\\d{1,3})|(192\\.168\\.\\d{1,3}\\.\\d{1,3})$";
        if (Pattern.matches(pattern,realIp)){
            operLog.setOperLocation("内网Ip");
        }else{
            operLog.setOperLocation("外网Ip");
        }

        //获取返回参数
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
        Object o = JSON.toJavaObject(jsonObject, Object.class);
        operLog.setJsonResult(String.valueOf(o));
        // 操作时间
        operLog.setOperTime(new Date());
        if (e != null) {
            operLog.setStatus(1);
            // IotLicenseException为本系统自定义的异常类，读者若要获取异常信息，请根据自身情况变通
            operLog.setErrorMsg(e.getMessage());
        }
        // 处理注解上的参数
        getControllerMethodDescription(joinPoint, controllerLog, operLog);
        // 保存数据库
        sysOperLogService.addOperlog(operLog);
    }

    /**
     * 是否存在注解，如果存在就获取，不存在则返回null
     * @param joinPoint
     * @return
     */
    private MyLog getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(MyLog.class);
        }
        return null;
    }

    /**
     * 获取Controller层上MyLog注解中对方法的描述信息
     * @param joinPoint 切点
     * @param myLog 自定义的注解
     * @param operLog 操作日志实体类
     */
    private void getControllerMethodDescription(JoinPoint joinPoint, MyLog myLog, SysOperLog operLog) {
        // 设置业务类型（0其它 1新增 2修改 3删除）
        operLog.setBusinessType(myLog.businessType().ordinal());
        // 设置模块标题，eg:登录
        operLog.setTitle(myLog.title());
        // 对方法上的参数进行处理，处理完：userName=xxx,password=xxx
        String optParam = getAnnotationValue(joinPoint, myLog.optParam());
        operLog.setOperParam(optParam);
        //操作类别（0其它 1后台用户 2手机端用户）
        operLog.setOperatorType(myLog.operatorType().ordinal());
    }

    /**
     * 对方法上的参数进行处理
     * @param joinPoint
     * @param name
     * @return
     */
    private String getAnnotationValue(JoinPoint joinPoint, String name) {
        String paramName = name;
        // 获取方法中所有的参数
        Map<String, Object> params = getParams(joinPoint);
        // 参数是否是动态的:#{paramName}
        if (paramName.matches("^#\\{\\D*\\}")) {
            // 获取参数名,去掉#{ }
            paramName = paramName.replace("#{", "").replace("}", "");
            // 是否是复杂的参数类型:对象.参数名
            if (paramName.contains(".")) {
                String[] split = paramName.split("\\.");
                // 获取方法中对象的内容
                Object object = getValue(params, split[0]);
                // 转换为JsonObject
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(object);
                // 获取值
                Object o = jsonObject.get(split[1]);
                return String.valueOf(o);
            } else {// 简单的动态参数直接返回
                StringBuilder str = new StringBuilder();
                String[] paraNames = paramName.split(",");
                for (String paraName : paraNames) {

                    String val = String.valueOf(getValue(params, paraName));
                    // 组装成 userName=xxx,password=xxx,
                    str.append(paraName).append("=").append(val).append(",");
                }
                // 去掉末尾的,
                if (str.toString().endsWith(",")) {
                    String substring = str.substring(0, str.length() - 1);
                    return substring;
                } else {
                    return str.toString();
                }
            }
        }
        // 非动态参数直接返回
        return name;
    }

    /**
     * 获取方法上的所有参数，返回Map类型, eg: 键："userName",值:xxx  键："password",值:xxx
     * @param joinPoint
     * @return
     */
    public Map<String, Object> getParams(JoinPoint joinPoint) {
        Map<String, Object> params = new HashMap<>(8);
        // 通过切点获取方法所有参数值["zhangsan", "123456"]
        Object[] args = joinPoint.getArgs();
        // 通过切点获取方法所有参数名 eg:["userName", "password"]
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] names = signature.getParameterNames();
        for (int i = 0; i < args.length; i++) {
            params.put(names[i], args[i]);
        }
        return params;
    }

    /**
     * 从map中获取键为paramName的值，不存在放回null
     * @param map
     * @param paramName
     * @return
     */
    private Object getValue(Map<String, Object> map, String paramName) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals(paramName)) {
                return entry.getValue();
            }
        }
        return null;
    }

}
