package com.web.design;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }
}
