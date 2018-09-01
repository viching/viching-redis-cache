package com.viching.redis.cache.test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONObject;
import com.viching.redis.cache.App;
import com.viching.redis.cache.test.service.CacheService;
import com.viching.redis.cache.test.service.Student;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AspectTestApp {
    
    @Autowired
    private CacheService cacheService;
    
    public AspectTestApp() {
        // TODO Auto-generated constructor stub
    }
    
    
    public void test(){
        
        cacheService.callback("good", "g,o,o,d is good");
        
        Student student = new Student();
        student.setId("abcd");
        student.setName("张三");
        
        cacheService.save(student);
        
    }
    
    @Test
    public void test1(){
        
        Student a = new Student();
        a.setId("abcd");
        //a.setName("张三");
        a.setBirth(new Date());
        cacheService.save(a);
        
        Student b = cacheService.query("abcd");
        
        System.out.println(">>> "+JSONObject.toJSON(b));
    }

}
