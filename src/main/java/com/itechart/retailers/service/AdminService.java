package com.itechart.retailers.service;

import com.itechart.retailers.model.entity.Location;
import com.itechart.retailers.model.entity.User;
import com.itechart.retailers.repository.projection.UserView;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AdminService {

    List<Location> findLocations(Long customerId);

    boolean createLocation(Location location, Long customerId);

    void deleteLocation(Long id);

    void deleteLocations(Set<Long> ids);

    List<UserView> findEmployees(Long customerId);

    boolean createUser(User user, Long customerId);

    Optional<Long> findCustomerId(String adminEmail);
}
