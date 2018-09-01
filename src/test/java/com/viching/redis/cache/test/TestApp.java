package com.viching.redis.cache.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import redis.clients.jedis.JedisCluster;

import com.viching.redis.cache.App;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestApp {
    
    @Autowired
    private JedisCluster cluster;
    
    public TestApp() {
        // TODO Auto-generated constructor stub
    }
    
    @Test
    public void test(){
        
        String result = cluster.get("aa");
        
        System.out.println(">>>result: "+result);

        cluster.set("test", "0000000000000");
        
        result = cluster.get("test");
        
        System.out.println(">>>result: "+result);
        Assert.assertEquals("0000000000000", result);
    }

}
