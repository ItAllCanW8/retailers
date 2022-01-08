package com.itechart.retailers.controller;

import com.itechart.retailers.model.dto.BillDto;
import com.itechart.retailers.model.entity.Bill;
import com.itechart.retailers.model.payload.response.MessageResp;
import com.itechart.retailers.security.service.SecurityContextService;
import com.itechart.retailers.service.BillService;
import com.itechart.retailers.service.exception.ItemAmountException;
import com.itechart.retailers.service.exception.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.itechart.retailers.controller.constant.Message.BILL_CREATED_MSG;
import static com.itechart.retailers.security.constant.Authority.SHOP_MANAGER_ROLE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BillController {

	public static final String POST_BILLS_MAPPING = "/bills";
	public static final String GET_BILLS_MAPPING = "/bills";
	private static final String AUTHORITIES = "hasRole('" + SHOP_MANAGER_ROLE + "')";

	private final BillService billService;
	private final SecurityContextService securityService;

	@PostMapping(POST_BILLS_MAPPING)
	@PreAuthorize(AUTHORITIES)
	public ResponseEntity<?> createBill(@RequestBody Bill bill) throws ItemAmountException, ItemNotFoundException {
		Long shopManagerId = securityService.getCurrentUserId();
		Long locationId = securityService.getCurrentLocationId();
		billService.createBill(bill, locationId, shopManagerId);
		return ResponseEntity.ok(new MessageResp(BILL_CREATED_MSG));
	}

	@GetMapping(GET_BILLS_MAPPING)
	@PreAuthorize(AUTHORITIES)
	public List<BillDto> loadShopBills() {
		Long locationId = securityService.getCurrentLocationId();
		return billService.loadShopBills(locationId);
	}
}
