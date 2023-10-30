package com.ads.main.core.config;


import com.ads.main.enums.cache.CacheType;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {

        List<CaffeineCache> caches =  Arrays.stream(CacheType.values()).map(cacheType ->
                new CaffeineCache(cacheType.getName(),
                        Caffeine.newBuilder()
                        .expireAfterWrite(cacheType.getExpiredAfterWriter(), TimeUnit.SECONDS)
                        .recordStats()
                        .build())
                ).toList();

        SimpleCacheManager cacheManager = new SimpleCacheManager();

        cacheManager.setCaches(caches);

        return cacheManager;
    }
}
