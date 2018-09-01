package com.viching.redis.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ComplexCache {
    
    PullCache[] cacheable() default {};    

    PushCache[] put() default {};     

    RemoveCache[] evict() default {};
}
