package com.substring.ott.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class PageController {

//    home page

    @GetMapping("/home")
    public String home(){
        return "home";
    }


//    dashboard

    @GetMapping("/dashboard")
    public String dashboard(){
        return "dashboard";
    }


    //token generated success message

    @GetMapping("/success-token")
    public String successToken(){
        return "success_token";
    }


}
