package com.viching.redis.cache.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

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
public class MethodLogAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(public * *(..))")
    public void log() {
    }
    
    @AfterThrowing(pointcut = "log()", throwing="throwable")
    public void throwException(Throwable throwable){
        logger.debug(">>> throwable.getMessage(): "+throwable.getMessage());
        logger.debug(">>> throwable.getStackTrace(): "+throwable.getStackTrace());
    }
}
