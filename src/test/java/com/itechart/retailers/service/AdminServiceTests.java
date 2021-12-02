package com.itechart.retailers.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdminServiceTests {

    @Autowired
    private AdminService adminService;

    @Test
    void findLocations() {
        System.out.println(adminService.findLocations("test@test.com"));
    }
}
