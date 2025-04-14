package com.example.exchange_app.model.config;

import com.example.exchange_app.model.CacheConstans;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisCacheConfig {
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory("localhost", 6379);  // lub inny adres Redis
    }
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.builder(redisConnectionFactory)
                .withCacheConfiguration(CacheConstans.FIND_ALL, RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofHours(4L)))
                .withCacheConfiguration(CacheConstans.FIND_BY_CODE, RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofHours(4L))
                        .disableCachingNullValues())  // opcjonalnie, jeśli nie chcesz cache'ować nulli
                .build();
    }
}
