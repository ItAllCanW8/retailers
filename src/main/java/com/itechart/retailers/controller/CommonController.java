package com.itechart.retailers.controller;

import com.itechart.retailers.model.entity.Location;
import com.itechart.retailers.model.entity.User;
import com.itechart.retailers.repository.LocationRepository;
import com.itechart.retailers.repository.UserRepository;
import com.itechart.retailers.security.service.SecurityContextService;
import com.itechart.retailers.service.LocationService;
import com.itechart.retailers.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommonController {

    private final UserService userService;
    private final LocationService locationService;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final SecurityContextService securityService;

    @GetMapping("/current-location")
    @PreAuthorize("hasAuthority('DISPATCHER')")
    public Location getCurrentLocation() {
        return securityService.getCurrentLocation();
    }

    @GetMapping("/locations-except-current")
    @PreAuthorize("hasAuthority('DISPATCHER')")
    public List<Location> getLocationsExceptCurrent() {
        Long currentCustomerId = securityService.getCurrentCustomer().getId();
        Long currentLocationId = securityService.getCurrentLocation().getId();
        return locationRepository.findLocationsByCustomerIdAndIdNot(currentCustomerId, currentLocationId);
    }

    @GetMapping("/managers")
    @PreAuthorize("hasAuthority('DISPATCHER')")
    public List<User> getManagers() {
        return userService.getUsersByRoleNameInCurrentCustomer("WAREHOUSE_MANAGER");
    }
}
