package com.zhongshijie1995.big_boot.base.util.cost;

import com.alibaba.fastjson2.JSONObject;
import com.zhongshijie1995.big_boot.base.protocol.RespBody;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
@Slf4j(topic = "CostReport")
public class RespCostAspect {

    private static final String COST = "cost";
    private static final String FUNC = "func";
    private static final String RESP_CODE = "respCode";
    private static final String RESP_DESC = "respDesc";

    @Around("@annotation(ect)")
    public Object doAround(ProceedingJoinPoint pjp, RespCost ect) throws Throwable {
        // 统计开销
        long begin = System.currentTimeMillis();
        Object obj = pjp.proceed();
        long end = System.currentTimeMillis();
        // 打印开销报告
        JSONObject cost = new JSONObject();
        cost.put(COST, getCost(begin, end));
        cost.put(FUNC, getFunc(pjp));
        cost.put(RESP_CODE, ((RespBody)obj).getRespCode());
        cost.put(RESP_DESC, ((RespBody)obj).getRespDesc());
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
        List<String> args = new ArrayList<>();
        if (argNames.length == argValues.length) {
            for (int i = 0; i < argNames.length; i++) {
                args.add(String.format("%s=%s", argNames[i], argValues[i]));
            }
        }
        return String.format("%s$%s(%s)", className, methodName, String.join(",", args));
    }
}
