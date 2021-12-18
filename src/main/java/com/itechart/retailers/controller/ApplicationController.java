package com.itechart.retailers.controller;

import com.itechart.retailers.model.entity.Application;
import com.itechart.retailers.model.payload.request.ApplicationReq;
import com.itechart.retailers.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/application")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;
    private final String authorities = "hasAuthority('DISPATCHER') or hasAuthority('WAREHOUSE_MANAGER')" +
            " or hasAuthority('SHOP_MANAGER')";


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
    public void create(@RequestBody ApplicationReq applicationReq) {
        applicationService.save(applicationReq);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(authorities)
    public void deleteById(@PathVariable Long id) {
        applicationService.deleteById(id);
    }
}
