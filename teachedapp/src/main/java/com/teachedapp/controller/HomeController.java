package com.teachedapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    static final String VIEW_INDEX = "home/index";

    @RequestMapping("/")
    String index(){
        return VIEW_INDEX;
    }

}

