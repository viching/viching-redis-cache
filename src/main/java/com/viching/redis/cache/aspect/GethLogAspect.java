package com.viching.redis.cache.aspect;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.viching.redis.cache.annotation.GatherLog;
import com.viching.redis.cache.util.ReflectTools;

/**
 * 方法执行抛出异常处理
 * @project viching-redis-cache
 * @author Administrator
 * @date 2018年9月1日
 * Copyright (C) 2016-2018 www.viching.com Inc. All rights reserved.
 */
@Aspect
@Order(1)
@Component
public class GethLogAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("@annotation(com.viching.redis.cache.annotation.GatherLog)")
    public void gatherLog() {
    }
    
    @AfterReturning(pointcut = "gatherLog()")
    public void operationLog(JoinPoint joinPoint) throws Exception {
        //获取方法参数
        List<Object> list = ReflectTools.adapter(joinPoint, GatherLog.class);
        
        logger.debug(">>> method: "+JSONObject.toJSON(list).toString());
    }
    @AfterThrowing(pointcut = "gatherLog()", throwing="throwable")
    public void throwException(Throwable throwable){
        logger.debug(">>> throwable.getMessage(): "+throwable.getMessage());
        logger.debug(">>> throwable.getStackTrace(): "+throwable.getStackTrace());
    }
}
