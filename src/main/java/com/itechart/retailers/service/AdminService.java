package com.itechart.retailers.service;

import com.itechart.retailers.model.entity.Location;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AdminService {

    @Deprecated
    List<Location> findLocations(String adminEmail);

    List<Location> findLocations(Long customerId);

    boolean createLocation(Long customerId, Location location);

    void deleteLocation(Long id);

    void deleteLocations(Set<Long> ids);

    Optional<Long> findCustomerId(String adminEmail);
}
