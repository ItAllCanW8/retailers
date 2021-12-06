package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.entity.CustomerLocation;
import com.itechart.retailers.model.entity.Location;
import com.itechart.retailers.model.entity.User;
import com.itechart.retailers.repository.*;
import com.itechart.retailers.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final LocationRepository locationRepo;
    private final CustomerRepository customerRepo;
    private final CustomerLocationRepository customerLocationRepo;
    private final AddressRepository addressRepo;
    private final UserRepository userRepo;

    @Override
    public List<Location> findLocations(String adminEmail) {
        Long customerId = customerRepo.findByAdminEmail(adminEmail).get().getId();

        return locationRepo.findLocationsByCustomerAssocCustomerId(customerId);
    }

    @Override
    public List<Location> findLocations(Long customerId) {
        return locationRepo.findLocationsByCustomerAssocCustomerId(customerId);
    }

    @Override
    public List<User> findUsers(Long customerId) {
        return userRepo.findUsersByLocationCustomerAssocCustomerId(customerId);
    }

    @Override
    @Transactional
    public boolean createLocation(Long customerId, Location location) {
        addressRepo.save(location.getAddress());

        return customerLocationRepo.save(CustomerLocation.builder()
                .location(locationRepo.save(location))
                .customer(customerRepo.getById(customerId))
                .build()).getId() != null;
    }

    @Override
    @Transactional
    public void deleteLocation(Long id) {
        locationRepo.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteLocations(Set<Long> ids) {
        locationRepo.deleteAllByIdInBatch(ids);
    }

    @Override
    public Optional<Long> findCustomerId(String adminEmail) {
        return Optional.of(customerRepo.findByAdminEmail(adminEmail).get().getId());
    }
}
