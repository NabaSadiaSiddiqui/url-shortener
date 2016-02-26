package com.naba.url.shortener.controller;

import com.naba.url.shortener.model.UrlState;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UrlShortenerController {

    @RequestMapping(method = RequestMethod.GET)
    public String defaultPage(Model model) {
        model.addAttribute("urlShortner", new UrlState());
        return "index";
    }

    @RequestMapping(value = "/url-shortner", method = RequestMethod.POST)
    public String urlShortener(@ModelAttribute UrlState url, Model model) {
        url.setShortnedUrl(shortenUrl(url.getOriginalUrl()));
        model.addAttribute("urlShortner", url);
        return "index";
    }

    private String shortenUrl(String originalUrl) {
        String shortenedUrl = String.valueOf(originalUrl.hashCode());
        return shortenedUrl;
    }
}
