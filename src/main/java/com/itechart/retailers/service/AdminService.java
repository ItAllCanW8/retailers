package com.itechart.retailers.service;

import com.itechart.retailers.model.entity.Location;

import java.util.List;

public interface AdminService {
    List<Location> findLocations(String adminEmail);
}
