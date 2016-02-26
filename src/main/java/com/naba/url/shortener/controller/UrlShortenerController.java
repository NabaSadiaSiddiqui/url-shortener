package com.naba.url.shortener.controller;

import com.naba.url.shortener.model.UrlShortener;
import com.naba.url.shortener.model.UrlState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UrlShortenerController {

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
        url.setShortenedUrl(shortenedUrl);
        model.addAttribute("urlShortener", url);
        return "index";
    }
}
