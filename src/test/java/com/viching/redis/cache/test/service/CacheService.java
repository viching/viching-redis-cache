package com.viching.redis.cache.test.service;

public interface CacheService {
    
    Student query(String id);
    
    void save(Student student);
    
    void delete(String id);
    
    void callback(String id, String detail);
}
