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

import static com.itechart.retailers.security.constant.Authority.DIRECTOR_ROLE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CustomerCategoryController {

    public static final String GET_CATEGORIES_MAPPING = "/categories";
    private static final String AUTHORITIES = "hasRole('" + DIRECTOR_ROLE + "')";

    private final CategoryService categoryService;
    private final SecurityContextService securityService;

    @GetMapping(GET_CATEGORIES_MAPPING)
    @PreAuthorize(AUTHORITIES)
    public List<CustomerCategory> loadCustomerCategories() {
        return categoryService.loadCustomerCategories(securityService.getCurrentCustomerId());
    }
}
