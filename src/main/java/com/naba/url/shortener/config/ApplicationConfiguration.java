package com.naba.url.shortener.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class ApplicationConfiguration {
    @Bean
    Jedis jedis() {
        return new Jedis("localhost", 6379);
    }
}
