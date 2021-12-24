package com.itechart.retailers.controller;

import com.itechart.retailers.model.dto.BillDto;
import com.itechart.retailers.model.entity.Bill;
import com.itechart.retailers.model.payload.response.MessageResp;
import com.itechart.retailers.security.service.SecurityContextService;
import com.itechart.retailers.service.BillService;
import com.itechart.retailers.service.exception.ItemAmountException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BillController {

    private final BillService billService;
    private final SecurityContextService securityService;
    private final String roles = "hasRole('SHOP_MANAGER')";

    @PostMapping("/bills")
    @PreAuthorize(roles)
    public ResponseEntity<?> createBill(@RequestBody Bill bill) {
        Long shopManagerId = securityService.getCurrentUserId();
        Long locationId = securityService.getCurrentLocationId();

        try {
            billService.createBill(bill, locationId, shopManagerId);
        } catch (ItemAmountException e){
            return ResponseEntity.badRequest().body(new MessageResp(e.getMessage()));
        }

        return ResponseEntity.ok(new MessageResp("Bill created."));
    }

    @GetMapping("/bills")
    @PreAuthorize(roles)
    public List<BillDto> loadShopBills() {
        Long locationId = securityService.getCurrentLocationId();

        return billService.loadShopBills(locationId);
    }
}
