package com.itechart.retailers.controller;

import com.itechart.retailers.controller.constant.Message;
import com.itechart.retailers.model.entity.Supplier;
import com.itechart.retailers.model.payload.response.MessageResp;
import com.itechart.retailers.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.itechart.retailers.security.constant.Authority.ADMIN_ROLE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SupplierController {
    public static final String GET_SUPPLIERS_MAPPING = "/suppliers";
    public static final String POST_SUPPLIERS_MAPPING = "/suppliers";
    public static final String PUT_SUPPLIERS_MAPPING = "/suppliers/{id}";
    private final static String AUTHORITIES = "hasRole('" + ADMIN_ROLE + "')";

    private final AdminService adminService;

    @GetMapping(GET_SUPPLIERS_MAPPING)
    @PreAuthorize(AUTHORITIES)
    public List<Supplier> getSuppliers() {
        return adminService.findSuppliers();
    }

    @PostMapping(POST_SUPPLIERS_MAPPING)
    @PreAuthorize(AUTHORITIES)
    public ResponseEntity<?> createSupplier(@RequestBody Supplier supplier) {
        supplier.setActive(true);
        adminService.createSupplier(supplier);
        return ResponseEntity.ok(new MessageResp(Message.SUPPLIER_CREATED_MSG));
    }

    @PutMapping(PUT_SUPPLIERS_MAPPING)
    @PreAuthorize(AUTHORITIES)
    public ResponseEntity<?> updateSupplierStatus(@PathVariable Long id, @RequestBody boolean isActive) {
        adminService.updateSupplierStatus(id, isActive);
        return ResponseEntity.ok(new MessageResp(Message.STATUS_UPDATED_MSG));
    }
}
