package com.itechart.retailers.security.service;

import com.itechart.retailers.model.entity.Customer;
import com.itechart.retailers.model.entity.Location;
import com.itechart.retailers.model.entity.User;
import com.itechart.retailers.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityContextService {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(userEmail).get();
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public Customer getCurrentCustomer() {
        return getCurrentUser().getCustomer();
    }

    public Long getCurrentCustomerId() {
        return getCurrentUser().getCustomer().getId();
    }

    public Long getCurrentLocationId() { return getCurrentUser().getLocation().getId(); }

    public Location getCurrentLocation() {
        return getCurrentUser().getLocation();
    }
}
