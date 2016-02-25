package com.naba.url.shortner;

public class UrlShortner {
    private String originalUrl;

    private String shortnedUrl;

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortnedUrl() {
        return shortnedUrl;
    }

    public void setOriginalUrl(String url) {
        this.originalUrl = url;
    }

    public void setShortnedUrl(String url) {
        this.shortnedUrl = url;
    }
}
