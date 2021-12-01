package com.itechart.retailers.repository;

import com.itechart.retailers.model.entity.Location;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class LocationRepoTests {
    @Autowired
    private LocationRepository locationRepository;

    @Test
    void findLocationsByCustomerAssocCustomerId() {
        List<Location> locationList = locationRepository.findLocationsByCustomerAssocCustomerId(1L);

        System.out.println(locationList);
    }
}
