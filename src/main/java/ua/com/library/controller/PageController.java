package ua.com.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    @RequestMapping("/api")
    public String mainPage(){
        return "index.html";
    }
    @RequestMapping("/")
    public String index(){
        return "index.html";
    }



}