package com.gumieiro.devchallenge.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "pages/index";
    }

    @GetMapping("/error")
    public String error() {
        return "pages/index";
    }
}
