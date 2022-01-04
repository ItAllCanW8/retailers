package com.itechart.retailers.controller;

import com.itechart.retailers.model.payload.response.LocationItemResp;
import com.itechart.retailers.service.LocationItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.itechart.retailers.security.constant.Authority.LOCATION_GET_AUTHORITY;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LocationItemController {

    public static final String GET_CURRENT_LOCATION_ITEMS_MAPPING = "/current-location-items";
    public static final String AUTHORITIES = "hasAuthority('" + LOCATION_GET_AUTHORITY + "')";
    private final LocationItemService locationItemService;

    @GetMapping(GET_CURRENT_LOCATION_ITEMS_MAPPING)
    @PreAuthorize(AUTHORITIES)
    public List<LocationItemResp> getCurrentLocation() {
        return locationItemService.getCurrentLocationItems();
    }
}
