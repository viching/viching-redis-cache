package com.viching.redis.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 响应参数
 * 
 * @project viching-redis-cache
 * @author Administrator
 * @date 2018年9月2日
 * Copyright (C) 2016-2018 www.viching.com Inc. All rights reserved.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseParams {
    
    String value() default "";
    
    String desc() default "";
    
}
