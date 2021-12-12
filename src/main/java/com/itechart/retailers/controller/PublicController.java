package com.itechart.retailers.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PublicController {

    @GetMapping("/")
    public String getPublicContent() {
        return "Public Content.";
    }
}
