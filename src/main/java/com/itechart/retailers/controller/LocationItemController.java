package com.itechart.retailers.controller;

import com.itechart.retailers.model.payload.response.LocationItemResp;
import com.itechart.retailers.service.LocationItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LocationItemController {

    private final LocationItemService locationItemService;

    @GetMapping("/current-location-items")
    @PreAuthorize("hasAuthority('location:get')")
    public List<LocationItemResp> getCurrentLocation() {
        return locationItemService.getCurrentLocationItems();
    }
}
