package com.zhongshijie1995.big_boot.base.util.cost;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j(topic = "CostReport")
public class CostReportAspect {

    private static final String COST = "cost";
    private static final String FUNC = "func";

    @Around("@annotation(ect)")
    public Object doAround(ProceedingJoinPoint pjp, CostReport ect) throws Throwable {
        // 统计开销
        long begin = System.currentTimeMillis();
        Object obj = pjp.proceed();
        long end = System.currentTimeMillis();
        // 打印开销报告
        JSONObject cost = new JSONObject();
        cost.put(COST, getCost(begin, end));
        cost.put(FUNC, getFunc(pjp));
        log.info(cost.toString());
        // 继续原函数返回
        return obj;
    }

    /**
     * 获取当前方法执行耗时
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @return 耗时
     */
    public long getCost(long begin, long end) {
        return end - begin;
    }

    /**
     * 获取当前方法描述
     *
     * @param pjp 当前切点
     * @return 运行描述
     */
    public String getFunc(ProceedingJoinPoint pjp) {
        String className = pjp.getSignature().getDeclaringTypeName();
        String methodName = pjp.getSignature().getName();
        String[] argNames = ((MethodSignature)pjp.getSignature()).getParameterNames();
        Object[] argValues = pjp.getArgs();
        StringBuilder args = new StringBuilder();
        if (argNames.length == argValues.length) {
            for (int i = 0; i < argNames.length; i++) {
                args.append(argNames[i]).append("=").append(argValues[i]).append(",");
            }
        }
        return String.format("%s$%s(%s)", className, methodName, args);
    }
}
