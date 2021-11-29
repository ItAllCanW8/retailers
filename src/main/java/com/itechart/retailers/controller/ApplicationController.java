package com.itechart.retailers.controller;

import com.itechart.retailers.model.Application;
import com.itechart.retailers.model.dto.ApplicationDto;
import com.itechart.retailers.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
@CrossOrigin("*")
public class ApplicationController {
    private final ApplicationService applicationService;
    private final String authorities = "hasAuthority('DISPATCHER') or hasAuthority('WAREHOUSE_MANAGER')" +
            " or hasAuthority('SHOP_MANAGER')";

    @Autowired
    public ApplicationController(ApplicationService applicationService){
        this.applicationService = applicationService;
    }

    @GetMapping
    @PreAuthorize(authorities)
    public List<Application> getAll() {
        return applicationService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize(authorities)
    public Application getById(@PathVariable Long id) {
        return applicationService.getById(id);
    }

    @PostMapping
    @PreAuthorize(authorities)
    public void create(@RequestBody ApplicationDto applicationDto){
        applicationService.save(applicationDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(authorities)
    public void deleteById(@PathVariable Long id) {
        applicationService.deleteById(id);
    }
}
