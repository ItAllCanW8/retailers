package com.itechart.retailers.controller;

import com.itechart.retailers.model.entity.CustomerCategory;
import com.itechart.retailers.security.service.SecurityContextService;
import com.itechart.retailers.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CustomerCategoryController {

    private final CategoryService categoryService;
    private final SecurityContextService securityService;
    private final String authorities = "hasRole('DIRECTOR')";

    @GetMapping("/categories")
    @PreAuthorize(authorities)
    public List<CustomerCategory> loadCustomerCategories() {
        return categoryService.loadCustomerCategories(securityService.getCurrentCustomerId());
    }
}
