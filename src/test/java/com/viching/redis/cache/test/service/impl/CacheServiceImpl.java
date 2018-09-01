package com.viching.redis.cache.test.service.impl;

import org.springframework.stereotype.Service;

import com.viching.redis.cache.annotation.PullCache;
import com.viching.redis.cache.annotation.PushCache;
import com.viching.redis.cache.annotation.RemoveCache;
import com.viching.redis.cache.test.service.CacheService;
import com.viching.redis.cache.test.service.Student;

@Service
public class CacheServiceImpl implements CacheService {
    
    @PullCache(value = "Student", key="id", expires=10)
    @Override
    public Student query(String id) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @PushCache(value = "Student", key = "student.id", expires = 10)
    @Override
    public void save(Student student) {
        // TODO Auto-generated method stub
        
    }

    @RemoveCache(value = "Student", key="id")
    @Override
    public void delete(String id) {
        // TODO Auto-generated method stub
        
    }
    
    @PushCache(value = "Cache", key = "id", target = "detail", expires = 10)
    @Override
    public void callback(String id, String detail) {
        // TODO Auto-generated method stub
        
    }

}
