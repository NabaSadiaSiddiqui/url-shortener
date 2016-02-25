package com.naba.url.shortner;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UrlShortnerController {

    @RequestMapping(method = RequestMethod.GET)
    public String defaultPage(Model model) {
        model.addAttribute("urlShortner", new UrlShortner());
        return "index";
    }

    @RequestMapping(value = "/url-shortner", method = RequestMethod.POST)
    public String urlShortner(@ModelAttribute UrlShortner url, Model model) {
        url.setShortnedUrl(url.getOriginalUrl() + "/shortned");
        model.addAttribute("urlShortner", url);
        return "index";
    }
}
