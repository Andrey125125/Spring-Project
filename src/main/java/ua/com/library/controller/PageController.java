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


    @RequestMapping("/reader")
    public String readerPage() {return "reader.html";}

    @RequestMapping("/admin")
    public String adminPage() {return "admin.html";}


//    @RequestMapping("/all_user")
//    public String userPage(){
//        return "users/index.html";
//    }
}