package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.entity.Location;
import com.itechart.retailers.model.entity.User;
import com.itechart.retailers.repository.CustomerRepository;
import com.itechart.retailers.repository.LocationRepository;
import com.itechart.retailers.repository.UserRepository;
import com.itechart.retailers.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {
    private final LocationRepository locationRepo;
    private final UserRepository userRepo;
    private final CustomerRepository customerRepo;

    @Override
    public List<Location> findLocations(String adminEmail) {
        Long adminId = userRepo.findByEmail(adminEmail).get().getId();
        Long customerId = customerRepo.findByAdminId(adminId).get().getId();

        return locationRepo.findLocationsByCustomerAssocCustomerId(customerId);
    }
}
