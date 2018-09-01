package com.viching.redis.cache.aspect;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;

/**
 * http请求日志记录
 * @project viching-redis-cache
 * @author Administrator
 * @date 2018年9月1日
 * Copyright (C) 2016-2018 www.viching.com Inc. All rights reserved.
 */
@Aspect
@Order(1)
@Component
public class HttpLogAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void log() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void getLog() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postLog() {
    }

    @Before("log() || getLog() || postLog()")
    public void runBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        logger.info("URL : {}", request.getRequestURL() + " [" + request.getMethod() + "]");
        logger.info("IP : {}", request.getRemoteAddr());
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String nextElement = headerNames.nextElement();
            logger.info(nextElement.toUpperCase() + " : {}", request.getHeader(nextElement));
        }
        logger.info("CLASS_METHOD : {}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("PARAM : {}", null != request.getQueryString() ? JSON.toJSONString(request.getQueryString().split("&")) : "EMPTY");
    }

    @AfterReturning(returning = "response", pointcut = "log() || getLog() || postLog()")
    public void runAfterReturning(Object response) {
        logger.info("RESPONSE : {}", JSON.toJSONString(response));
        logger.info("SPEND TIME : {}", (System.currentTimeMillis() - startTime.get()) + "ms");
    }
}
