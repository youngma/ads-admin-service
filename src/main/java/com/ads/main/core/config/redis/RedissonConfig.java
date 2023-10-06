package com.ads.main.core.config.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Value("${spring.data.redis.default.host}")
    private String redisHost;

    @Value("${spring.data.redis.default.port}")
    private int redisPort;

    @Value("${spring.data.redis.default.database}")
    private int database;

    private static final String REDISSON_HOST_PREFIX = "redis://";

    @Bean("redissonClient")
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(REDISSON_HOST_PREFIX + redisHost + ":" + redisPort)
                .setDatabase(database);
        return Redisson.create(config);
    }
}
