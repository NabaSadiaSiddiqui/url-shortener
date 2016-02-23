package com.naba.url.shortner;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UrlShortnerController {

    @RequestMapping("/")
    public String landingPage() {
        return "index";
    }

    @RequestMapping("/url-shortner/{url}")
    public String urlShortner(@PathVariable String url, Model model) {
        model.addAttribute("name", url);
        return "gretting";
    }
}
