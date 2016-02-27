package com.naba.url.shortener.model;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.security.MessageDigest;

@Component
public class UrlShortener {

    @Autowired
    Jedis jedis;

    @Autowired
    MessageDigest messageDigest;

    private int counter = 0;

    public String shortenUrl(String originalUrl) throws SetRedisKeyValueException {
        String status;
        String hash = getHash(originalUrl);
        String shortenedUrl = jedis.get(hash);
        if(shortenedUrl != null) {
            return shortenedUrl;
        }
        else {
            counter++;
            shortenedUrl = Base64.encode(String.valueOf(counter).getBytes());
            status = jedis.set(hash, shortenedUrl);
            if(!status.equalsIgnoreCase("OK")) {
                throw new SetRedisKeyValueException("Unable to set hash->shortenedUrl in Redis");
            }
        }

        status = setValue(shortenedUrl, originalUrl);
        if(!status.equalsIgnoreCase("OK")) {
            throw new SetRedisKeyValueException("Unable to set shortenedUrl->originalUrl in Redis");
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

    private String getHash(String input) {
        messageDigest.update(input.getBytes());
        byte[] hashBytes = messageDigest.digest();
        return Base64.encode(hashBytes);
    }
}
