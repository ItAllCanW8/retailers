package com.itechart.retailers.controller;

import com.itechart.retailers.model.entity.Location;
import com.itechart.retailers.service.AdminService;
import com.itechart.retailers.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;
    private final String authorities = "hasAuthority('ADMIN')";

    @GetMapping("/locations")
    @PreAuthorize(authorities)
    public List<Location> getLocations(Authentication authentication) {
        String adminEmail = authentication.getName();

//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        if (principal instanceof UserDetails) {
//            String email = ((UserDetails)principal).getUsername();
//        } else {
//            String username = principal.toString();
//        }

        return adminService.findLocations(adminEmail);
    }
}
