package com.viching.redis.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PullCache {
    
    String value() default ""; //缓存hset key1

    String key() default ""; //缓存hset key2 或者set key
    
    long  expires() default 0l; //失效时间
    
}
