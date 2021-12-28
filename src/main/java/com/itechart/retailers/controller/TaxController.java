package com.itechart.retailers.controller;

import com.itechart.retailers.model.payload.request.UpdCategoryTax;
import com.itechart.retailers.model.payload.request.UpdRentalTaxReq;
import com.itechart.retailers.model.payload.response.MessageResp;
import com.itechart.retailers.service.TaxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TaxController {

    private final TaxService taxService;
    private final String authorities = "hasRole('DIRECTOR')";

    @PutMapping("/taxes/rental")
    @PreAuthorize(authorities)
    public ResponseEntity<?> updRentalTax(@RequestBody UpdRentalTaxReq updRentalTaxReq) {
        if(taxService.updateRentalTax(updRentalTaxReq.getShopId(), updRentalTaxReq.getNewRentalTax())){
            return ResponseEntity.ok(new MessageResp("Rental tax for selected shop updated."));
        }
        else{
            return ResponseEntity.badRequest().body(new MessageResp("Error updating rental tax"));
        }
    }

    @PutMapping("/taxes/category")
    @PreAuthorize(authorities)
    public ResponseEntity<?> updCategoryTax(@RequestBody UpdCategoryTax updCategoryTaxReq) {
        if(taxService.updateItemCategoryTax(updCategoryTaxReq.getCustomerCategoryId(),
                updCategoryTaxReq.getNewCategoryTax())){
            return ResponseEntity.ok(new MessageResp("Category tax for selected category updated."));
        }
        else{
            return ResponseEntity.badRequest().body(new MessageResp("Error updating category tax"));
        }
    }
}
