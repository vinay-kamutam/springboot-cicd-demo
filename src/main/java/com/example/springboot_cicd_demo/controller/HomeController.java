package com.example.springboot_cicd_demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Hello from Spring Boot CI/CD Project!";
    }

    @GetMapping("/health")
    public String health() {
        return "Application is running successfully!";
    }
}
