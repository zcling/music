package com.qmx.redis.config;

import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@EnableCaching
@Configuration
public class RedisCacheConfig extends CachingConfigurerSupport {

    @Autowired
    private RedissonClient redissonClient;

    @Bean
    public CacheManager cacheManager() {
        Map<String, CacheConfig> config = new HashMap<String, CacheConfig>();
        config.put("redissonCache", new CacheConfig(30 * 60 * 1000, 15 * 60 * 1000));
        RedissonSpringCacheManager redissonSpringCacheManager = new RedissonSpringCacheManager(redissonClient);
        return redissonSpringCacheManager;
    }


}
