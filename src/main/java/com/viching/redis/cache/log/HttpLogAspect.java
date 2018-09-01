package com.viching.redis.cache.log;

import com.alibaba.fastjson.JSON;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author tan.dg
 * @date 2018/4/19 15:00
 */
@Aspect
@Order(1)
@Component
public class HttpLogAspect {

    private Logger LOG = LoggerFactory.getLogger(getClass());

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.tandg.plus.web..*.*(..))")
    public void httpLog() {}

    @Before("httpLog()")
    public void runBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        LOG.info("URL : {}", request.getRequestURL()+" ["+request.getMethod()+"]");
        LOG.info("IP : {}", request.getRemoteAddr());
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String nextElement = headerNames.nextElement();
            LOG.info(nextElement.toUpperCase() + " : {}", request.getHeader(nextElement));
        }
        LOG.info("CLASS_METHOD : {}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        LOG.info("PARAM : {}", null != request.getQueryString() ? JSON.toJSONString(request.getQueryString().split("&")) : "EMPTY");
    }

    @AfterReturning(returning = "response", pointcut = "httpLog()")
    public void runAfterReturning(Object response) {
        LOG.info("RESPONSE : {}", JSON.toJSONString(response));
        LOG.info("SPEND TIME : {}", (System.currentTimeMillis() - startTime.get()) + "ms");
    }
}
