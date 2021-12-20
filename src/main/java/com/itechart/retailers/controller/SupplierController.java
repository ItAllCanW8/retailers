package com.itechart.retailers.controller;

import com.itechart.retailers.model.entity.Supplier;
import com.itechart.retailers.model.payload.response.MessageResp;
import com.itechart.retailers.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SupplierController {
    private final AdminService adminService;
    private final String roles = "hasRole('ADMIN')";

    @GetMapping("/suppliers")
    @PreAuthorize(roles)
    public List<Supplier> getSuppliers() {
        return adminService.findSuppliers();
    }

    @PostMapping("/suppliers")
    @PreAuthorize(roles)
    public ResponseEntity<?> createSupplier(@RequestBody Supplier supplier) {
        supplier.setActive(true);
        adminService.createSupplier(supplier);

        return ResponseEntity.ok(new MessageResp("Supplier created."));
    }

    @PutMapping("/suppliers/{id}")
    @PreAuthorize(roles)
    public ResponseEntity<?> updateSupplierStatus(@PathVariable Long id, @RequestBody boolean isActive) {
        adminService.updateSupplierStatus(id, isActive);

        return ResponseEntity.ok(new MessageResp("Status updated."));
    }
}
