package com.itechart.retailers.service;

import com.itechart.retailers.model.entity.Location;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AdminService {

    List<Location> findLocations(Long customerId);

    boolean createLocation( Location location);

    void deleteLocation(Long id);

    void deleteLocations(Set<Long> ids);

    List<User> findUsers(Long customerId);

    Optional<Long> findCustomerId(String adminEmail);
}
