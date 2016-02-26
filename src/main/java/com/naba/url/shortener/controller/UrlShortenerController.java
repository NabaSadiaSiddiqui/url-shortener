package com.naba.url.shortener.controller;

import com.naba.url.shortener.model.GetRedisKeyValueException;
import com.naba.url.shortener.model.SetRedisKeyValueException;
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
import java.net.URISyntaxException;

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
    public String urlShortener(@ModelAttribute UrlState url, Model model) {
        String template = null;
        try {
            String shortenedUrl = urlShortener.shortenUrl(url.getOriginalUrl());
            url.setShortenedUrl(BASEURL.concat(shortenedUrl));
            model.addAttribute("urlShortener", url);
            template = "index";
        } catch (SetRedisKeyValueException e) {
            template = "error";
        }
        return template;
    }

    @RequestMapping(value = "/better-than-bitly/{url}", method = RequestMethod.GET)
    public ResponseEntity<String> redirect(@PathVariable String url) {
        ResponseEntity<String> responseEntity = null;
        try {
            URI originalUrl = new URI(urlShortener.getOriginalUrl(url));
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(originalUrl);
            responseEntity = new ResponseEntity<String>(httpHeaders, HttpStatus.SEE_OTHER);
        } catch (URISyntaxException e) {
            responseEntity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        } catch (GetRedisKeyValueException e) {
            responseEntity = new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            return responseEntity;
        }
    }
}
