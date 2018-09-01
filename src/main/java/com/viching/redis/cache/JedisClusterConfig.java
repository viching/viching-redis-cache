package com.viching.redis.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.viching.redis.cache.util.RedisObjectSerializer;

/**
 * jedis cluster config
 * @project viching-redis-cache
 * @author Administrator
 * @date 2018年8月25日
 * Copyright (C) 2016-2018 www.viching.com Inc. All rights reserved.
 */
@Configuration
@ConditionalOnClass({ RedisProperties.class })
public class JedisClusterConfig {
    
    //private final Logger logger = LoggerFactory.getLogger(getClass());
    
    //自动注入redis配置属性文件
    @Autowired
    private RedisProperties redisProperties;

    /*@Bean
    public JedisPool getJedisPool() {
        if(redisProperties.getHost() == null || redisProperties.getHost().isEmpty() || redisProperties.getPort() == 0){
            return null;
        }
        return new JedisPool(getJedisPoolConfig(), redisProperties.getHost(), redisProperties.getPort(), redisProperties.getTimeout());
    }

    @Bean
    public JedisCluster getJedisCluster() {
        Set<redis.clients.jedis.HostAndPort> nodes = new HashSet<>();
        if(redisProperties.getCluster() == null|| redisProperties.getCluster().getNodes() == null || redisProperties.getCluster().getNodes().size() == 0){
            return null;
        }
        for (String ipPort : redisProperties.getCluster().getNodes()) {
            logger.info(">>> ipPort:["+ipPort+"]");
            String[] ipPortPair = ipPort.split(":");
            nodes.add(new redis.clients.jedis.HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
        }
        return new JedisCluster(nodes, redisProperties.getTimeout(), 1000, redisProperties.getCluster().getMaxRedirects(), redisProperties.getPassword(), getJedisPoolConfig());//需要密码连接的创建对象方式
    }
    
    private JedisPoolConfig getJedisPoolConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        if(redisProperties.getJedis() != null && redisProperties.getJedis().getPool() != null){
            config.setMaxIdle(redisProperties.getJedis().getPool().getMaxIdle());
            config.setMaxTotal(redisProperties.getJedis().getPool().getMaxActive());
            config.setMaxWaitMillis(redisProperties.getJedis().getPool().getMaxWait());
        }
        // 在borrow一个jedis实例时，是否提前进行alidate操作；如果为true，则得到的jedis实例均是可用的；
        config.setTestOnBorrow(true);
        // 在还会给pool时，是否提前进行validate操作
        config.setTestOnReturn(true);
        return config;
    }*/
    
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisClusterConfiguration config = new RedisClusterConfiguration(redisProperties.getCluster().getNodes());
        config.setMaxRedirects(redisProperties.getCluster().getMaxRedirects());
        config.setPassword(RedisPassword.of(redisProperties.getPassword()));
        return new JedisConnectionFactory(config);
    }
 
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new RedisObjectSerializer());
        return template;
    }
}
