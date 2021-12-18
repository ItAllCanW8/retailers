package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.entity.Application;
import com.itechart.retailers.model.entity.ApplicationItem;
import com.itechart.retailers.model.entity.Location;
import com.itechart.retailers.model.entity.LocationItem;
import com.itechart.retailers.repository.ApplicationItemRepository;
import com.itechart.retailers.repository.ApplicationRepository;
import com.itechart.retailers.repository.LocationItemRepository;
import com.itechart.retailers.repository.LocationRepository;
import com.itechart.retailers.security.service.SecurityContextService;
import com.itechart.retailers.service.ApplicationService;
import com.itechart.retailers.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final SecurityContextService securityContextService;
    private final ApplicationService applicationService;
    private final ApplicationRepository applicationRepository;
    private final ApplicationItemRepository applicationItemRepository;
    private final LocationRepository locationRepository;
    private final LocationItemRepository locationItemRepository;

    @Override
    public boolean canAcceptApplication(Long applicationId) {
        Integer applicationOccupiedCapacity = applicationService.getOccupiedCapacity(applicationId);
        Integer locationAvailableCapacity = getCurrentAvailableCapacity();
        return applicationOccupiedCapacity <= locationAvailableCapacity;
    }

    @Override
    public Integer getCurrentAvailableCapacity() {
        int occupiedCapacity = 0;
        Location location = securityContextService.getCurrentLocation();
        for (LocationItem locationItem: location.getItemAssoc()) {
            occupiedCapacity += locationItem.getItem().getUnits() * locationItem.getAmount();
        }
        return location.getTotalCapacity() - occupiedCapacity;
    }

    @Override
    public void acceptApplication(Long applicationId) {
        Set<ApplicationItem> applicationItems = applicationRepository.getById(applicationId).getItemAssoc();
        Set<LocationItem> locationItems = applicationItems.stream()
                .map(this::createLocationItem)
                .collect(Collectors.toSet());
        locationItemRepository.saveAll(locationItems);

        Application application = applicationService.getById(applicationId);
        application.setStatus("FINISHED_PROCESSING");
        applicationRepository.save(application);
        applicationItemRepository.deleteAll(application.getItemAssoc());
    }

    private LocationItem createLocationItem(ApplicationItem ai) {
        return new LocationItem(ai.getAmount(), ai.getCost(), securityContextService.getCurrentLocation(), ai.getItem());
    }

}
