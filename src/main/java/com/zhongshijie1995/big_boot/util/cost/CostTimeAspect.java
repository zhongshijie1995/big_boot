package com.zhongshijie1995.big_boot.util.cost;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
@Slf4j
public class CostTimeAspect {
    @Around("@annotation(ect)")
    public Object doAround(ProceedingJoinPoint pjp, CostTime ect) throws Throwable {
        long begin = System.currentTimeMillis();
        Object obj = pjp.proceed();
        String className = pjp.getSignature().getDeclaringTypeName();
        String methodName = pjp.getSignature().getName();
        Object[] args = pjp.getArgs();
        List<String> paramsList = new ArrayList<>();
        for (Object arg : args) {
            paramsList.add(arg.toString());
        }
        String methodParams = String.join(",", paramsList);
        long end = System.currentTimeMillis();
        log.info("耗时 [{}] ms 用于执行 {}.{}({}) 方法", end - begin, className, methodName, methodParams);
        return obj;
    }
}
