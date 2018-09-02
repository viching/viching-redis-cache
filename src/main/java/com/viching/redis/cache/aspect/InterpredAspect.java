package com.viching.redis.cache.aspect;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.viching.redis.cache.annotation.Interpret;
import com.viching.redis.cache.util.ReflectTools;

/**
 * 将接口翻译成接口文档
 * @project viching-redis-cache
 * @author Administrator
 * @date 2018年9月2日
 * Copyright (C) 2016-2018 www.viching.com Inc. All rights reserved.
 */
@Aspect
@Order(1)
@Component
public class InterpredAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("@annotation(com.viching.redis.cache.annotation.Interpret)")
    public void interpred() {
    }
    
    @AfterReturning(pointcut = "interpred()")
    public void operationLog(JoinPoint joinPoint) throws Exception {
        //获取方法参数
        List<Object> list = ReflectTools.adapter(joinPoint, Interpret.class);
        
        logger.debug(">>> method: "+JSONObject.toJSON(list).toString());
    }
}
