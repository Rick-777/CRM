package com.lib.common.aspect;

import com.alibaba.fastjson.JSON;
import com.lib.common.annotation.SystemLog;
import com.lib.sys.entity.SysLog;
import com.lib.sys.service.ISysLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Date;

@Component
@Aspect
@EnableAspectJAutoProxy
public class SysLogAspect {

    @Autowired
    ISysLogService sysLogService;

    @Pointcut("@annotation(com.lib.common.SysLog)")
    public void logPointcut(){

    }
    @Around("logPointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // Time to start execute
        long begin = System.currentTimeMillis();
        Object obj = point.proceed();
        // Get the length of execution time
        long time = System.currentTimeMillis()-begin;
        // Save log
        saveSysLog(point, time);
        return obj;
    }

    /**
     * Save log
     *      User
     *      Detail execution
     *      Method of execution
     *      Param
     *      Execution time
     *      IP of client
     * @param point
     */
    private void saveSysLog(ProceedingJoinPoint point, long time) {
        SysLog sysLog = new SysLog();
        // Current login user
        // Get current method
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        // Need to get description of @SystemLog
        SystemLog systemLogAnno = method.getAnnotation(SystemLog.class);
        if(systemLogAnno != null){
            // Bind value of operation
            sysLog.setOperation(systemLogAnno.value());
        }
        // Bind name of method
        String className = point.getTarget().getClass().getName();
        String methodName = method.getName();
        sysLog.setMethod(className+"."+methodName+"()");
        // Get param of method
        Object[] paramsArgs = point.getArgs();
        String params = JSON.toJSON(paramsArgs).toString();
        sysLog.setParams(params);
        // Execution time
        sysLog.setTime(time);
        // Start time
        sysLog.setCreateDate(LocalDateTime.now());
        sysLogService.save(sysLog);
    }
}
