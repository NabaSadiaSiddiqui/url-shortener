package com.naba.url.shortener.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
public class UrlShortener {

    @Autowired
    Jedis jedis;

    public String shortenUrl(String originalUrl) throws SetRedisKeyValueException {
        String shortenedUrl = String.valueOf(originalUrl.hashCode());
        String status = setValue(shortenedUrl, originalUrl);
        if(!status.equalsIgnoreCase("OK")) {
            throw new SetRedisKeyValueException("Unable to set url in Redis");
        }
        return shortenedUrl;
    }

    public String getOriginalUrl(String shortenedUrl) throws GetRedisKeyValueException {
        String originalUrl = getValue(shortenedUrl);
        if(originalUrl == null) {
            throw new GetRedisKeyValueException("Unable to retrieve url from Redis");
        }
        return originalUrl;
    }

    private String getValue(String key) {
        return jedis.get(key);
    }

    private String setValue(String key, String value) {
        return jedis.set(key, value);
    }
}
