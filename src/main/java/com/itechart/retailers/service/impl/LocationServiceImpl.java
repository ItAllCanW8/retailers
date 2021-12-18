package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.entity.Location;
import com.itechart.retailers.security.service.SecurityContextService;
import com.itechart.retailers.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final SecurityContextService securityContextService;

    @Override
    public boolean locationCanAcceptApplication(Long applicationId) {
        Location location = securityContextService.getCurrentLocation();
        return false;
    }
}
