package com.itechart.retailers.controller;

import com.itechart.retailers.model.dto.ApplicationDto;
import com.itechart.retailers.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applications")
@CrossOrigin("*")
public class ApplicationController {
    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService){
        this.applicationService = applicationService;
    }

    @PostMapping
    public void create(@RequestBody ApplicationDto applicationDto){
        applicationService.save(applicationDto);
    }


}
