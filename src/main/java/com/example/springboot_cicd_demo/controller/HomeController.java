package com.example.springboot_cicd_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";   // Loads templates/index.html
    }

    @GetMapping("/health")
    @ResponseBody
    public String health() {
        return "Application is running successfully!";
    }
}
