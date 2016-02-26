package com.naba.url.shortener.model;

import lombok.Data;

@Data
public class UrlState {
    private String originalUrl;
    private String shortnedUrl;
}
