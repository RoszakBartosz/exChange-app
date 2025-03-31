package model.config;

import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Duration;

@Configuration
public class RedisCacheConfig {
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.builder(redisConnectionFactory)
                .withCacheConfiguration("FIND_ALL", RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofHours(4L)))
                .withCacheConfiguration("FIND_BY_CODE", RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofHours(4L)))
                .build();
    }
}
