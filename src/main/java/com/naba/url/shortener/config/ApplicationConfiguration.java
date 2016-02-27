package com.naba.url.shortener.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Configuration
public class ApplicationConfiguration {
    @Bean
    Jedis jedis() {
        return new Jedis("localhost", 6379);
    }

    @Bean
    MessageDigest messageDigest() {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
