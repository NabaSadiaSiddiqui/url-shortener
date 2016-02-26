package com.naba.url.shortener.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
public class UrlShortener {

    @Autowired
    Jedis jedis;

    public String shortenUrl(String originalUrl) throws Exception {
        String shortenedUrl = String.valueOf(originalUrl.hashCode());
        String status = setValue(originalUrl, shortenedUrl);
        if(!status.equalsIgnoreCase("OK")) {
            throw new Exception("Unable to set url in Redis");
        }
        return shortenedUrl;
    }

    private String setValue(String key, String value) {
        return jedis.set(key, value);
    }

    private String getValue(String key) {
        return jedis.get(key);
    }
}
