package com.zhongshijie1995.big_boot.base.util.cost;

import com.alibaba.fastjson2.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
@Slf4j(topic = "CostReport")
public class CostReportAspect {

    @Around("@annotation(ect)")
    public Object doAround(ProceedingJoinPoint pjp, CostReport ect) throws Throwable {
        long begin = System.currentTimeMillis();
        Object obj = pjp.proceed();
        long end = System.currentTimeMillis();
        CostJson costJson = new CostJson();
        costJson.setCost(getCostTime(begin, end));
        costJson.setDesc(getRunningDesc(pjp));
        costJson.setResult(getRunningResult(obj));
        log.info(getCostJsonString(costJson));
        return obj;
    }

    /**
     * 获取开销实体类的文本
     *
     * @param costJson 开销实体类
     * @return 开销实体类的文本
     */
    public String getCostJsonString(CostJson costJson) {
        return JSON.toJSONString(costJson);
    }

    /**
     * 获取方法耗时
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @return 耗时
     */
    public long getCostTime(long begin, long end) {
        return end - begin;
    }

    /**
     * 获取当前运行描述
     *
     * @param pjp 当前切点
     * @return 运行描述
     */
    public String getRunningDesc(ProceedingJoinPoint pjp) {
        String className = pjp.getSignature().getDeclaringTypeName();
        String methodName = pjp.getSignature().getName();
        Object[] args = pjp.getArgs();
        List<String> paramsList = new ArrayList<>();
        for (Object arg : args) {
            paramsList.add(arg.toString());
        }
        String methodParams = String.join(",", paramsList);
        return String.format("%s.%s(%s)", className, methodName, methodParams);
    }

    /**
     * 获取运行结果
     *
     * @param obj 运行
     * @return 运行结果
     */
    public String getRunningResult(Object obj) {
        return String.valueOf(obj);
    }

    /**
     * 开销实体类
     */
    @Data
    static class CostJson {
        long cost;
        String desc;
        String result;
    }
}
