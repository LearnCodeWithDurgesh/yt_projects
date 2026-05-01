package com.substring.sse.sseexample.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {


    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/status")
    public String status() {
        return "status";
    }
}
