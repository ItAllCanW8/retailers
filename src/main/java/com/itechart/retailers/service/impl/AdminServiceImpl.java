package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.entity.Location;
import com.itechart.retailers.repository.CustomerRepository;
import com.itechart.retailers.repository.LocationRepository;
import com.itechart.retailers.repository.UserRepository;
import com.itechart.retailers.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final LocationRepository locationRepo;
    private final UserRepository userRepo;
    private final CustomerRepository customerRepo;

    @Override
    public List<Location> findLocations(String adminEmail) {

        Long customerId = customerRepo.findByAdminEmail(adminEmail).get().getId();

        return locationRepo.findLocationsByCustomerAssocCustomerId(customerId);
    }
}
