package com.naba.url.shortener.controller;

import com.naba.url.shortener.model.UrlShortener;
import com.naba.url.shortener.model.UrlState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.URI;

@Controller
public class UrlShortenerController {

    private String BASEURL = "localhost:8080/better-than-bitly/";

    @Autowired
    UrlShortener urlShortener;

    @RequestMapping(method = RequestMethod.GET)
    public String defaultPage(Model model) {
        model.addAttribute("urlShortener", new UrlState());
        return "index";
    }

    @RequestMapping(value = "/url-shortener", method = RequestMethod.POST)
    public String urlShortener(@ModelAttribute UrlState url, Model model) throws Exception {
        String shortenedUrl = urlShortener.shortenUrl(url.getOriginalUrl());
        url.setShortenedUrl(BASEURL.concat(shortenedUrl));
        model.addAttribute("urlShortener", url);
        return "index";
    }

    @RequestMapping(value = "/better-than-bitly/{url}", method = RequestMethod.GET)
    public ResponseEntity<String> redirect(@PathVariable String url) throws Exception {
        URI originalUrl = new URI(urlShortener.getOriginalUrl(url));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(originalUrl);
        return new ResponseEntity<String>(httpHeaders, HttpStatus.SEE_OTHER);
    }
}
