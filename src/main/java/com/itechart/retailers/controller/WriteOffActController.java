package com.itechart.retailers.controller;

import com.itechart.retailers.model.dto.WriteOffActDto;
import com.itechart.retailers.model.entity.WriteOffAct;
import com.itechart.retailers.model.payload.response.MessageResp;
import com.itechart.retailers.security.service.SecurityContextService;
import com.itechart.retailers.service.WriteOffActService;
import com.itechart.retailers.service.exception.ItemAmountException;
import com.itechart.retailers.service.exception.UndefinedItemException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class WriteOffActController {
    private final String postAuthorities = "hasAuthority('DISPATCHER') or hasAuthority('SHOP_MANAGER')";
    private final String getLocalAuthorities = "hasAuthority('DISPATCHER') or hasAuthority('SHOP_MANAGER')" +
            " or hasAuthority('WAREHOUSE_MANAGER')";
    private final String getAllAuthorities = "hasAuthority('DIRECTOR')";

    private final SecurityContextService securityService;
    private final WriteOffActService writeOffActService;

    @PostMapping("/write-off-acts")
    @PreAuthorize(postAuthorities)
    public ResponseEntity<?> createWriteOffAct(@RequestBody WriteOffAct writeOffAct) {
        Long locationId = securityService.getCurrentLocationId();
        try {
            writeOffActService.save(writeOffAct, locationId);
        } catch (ItemAmountException e) {
            return ResponseEntity.badRequest().body(new MessageResp(e.getMessage()));
        }
        return ResponseEntity.ok(new MessageResp("Write-off act created."));
    }

    @GetMapping("/local-write-off-acts")
    @PreAuthorize(getLocalAuthorities)
    public List<WriteOffActDto> loadLocationWriteOffActs() {
        return writeOffActService.loadLocalWriteOffActs(securityService.getCurrentLocationId());

    }

    @GetMapping("/write-off-acts")
    @PreAuthorize(getAllAuthorities)
    public List<WriteOffActDto> loadAllWriteOffActs() {
        return writeOffActService.loadCustomerWriteOffActs(securityService.getCurrentCustomerId());
    }
}
