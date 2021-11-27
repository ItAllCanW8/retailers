package com.itechart.retailers.controller;

import com.itechart.retailers.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/application")
@CrossOrigin("*")
public class ApplicationController {
    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService){
        this.applicationService = applicationService;
    }
}
