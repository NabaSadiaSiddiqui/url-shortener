package com.naba.url.shortener;

import lombok.Data;

@Data
public class UrlShortner {
    private String originalUrl;
    private String shortnedUrl;
}
