package com.itechart.retailers.controller;

import com.itechart.retailers.model.entity.Application;
import com.itechart.retailers.model.payload.request.ApplicationReq;
import com.itechart.retailers.model.payload.request.DispatchItemReq;
import com.itechart.retailers.model.payload.response.MessageResp;
import com.itechart.retailers.security.service.SecurityContextService;
import com.itechart.retailers.service.ApplicationService;
import com.itechart.retailers.service.LocationService;
import com.itechart.retailers.service.exception.ItemAmountException;
import com.itechart.retailers.service.exception.UndefinedItemException;
import com.itechart.retailers.service.exception.UndefinedLocationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class ApplicationController {
	private final ApplicationService applicationService;
	private final LocationService locationService;
	private final String authorities = "hasAuthority('application:get') or hasAuthority('application:post')";
	private final SecurityContextService securityContextService;

	private Long customerId;

	@GetMapping("/applications")
	@PreAuthorize(authorities)
	public List<Application> getCurrentApplications() {
		customerId = securityContextService.getCurrentCustomerId();
		return applicationService.getCurrentApplications(customerId);
	}

	@GetMapping("/application/{id}")
	@PreAuthorize(authorities)
	public Application getById(@PathVariable Long id) {
		return applicationService.getById(id);
	}

	@PostMapping("/applications")
	@PreAuthorize(authorities)
	public ResponseEntity<?> create(@RequestBody ApplicationReq applicationReq) throws UndefinedItemException {
		customerId = securityContextService.getCurrentCustomerId();
		try {
			applicationService.save(applicationReq, customerId);
		} catch (UndefinedItemException e) {
			return ResponseEntity.badRequest().body(new MessageResp("Item is not found!"));
		}
		return ResponseEntity.ok(new MessageResp("Application created."));
	}

	@PostMapping("/dispatch-items")
	@PreAuthorize(authorities)
	public ResponseEntity<?> dispatchItems(@RequestBody DispatchItemReq dispatchItemReq) {
		customerId = securityContextService.getCurrentCustomerId();
		try {
			applicationService.dispatchItems(dispatchItemReq, customerId);
		} catch (ItemAmountException e) {
			return ResponseEntity.badRequest().body(new MessageResp("Incorrect item amount input!"));
		}
		return ResponseEntity.ok(new MessageResp("Items dispatched successfully"));
	}

	@PutMapping("/application/{id}/accept")
	@PreAuthorize(authorities)
	public ResponseEntity<?> acceptApplication(@PathVariable Long id) {
		if (!locationService.canAcceptApplication(id)) {
			return ResponseEntity.badRequest().body(new MessageResp("There is no space in location!"));
		}
		locationService.acceptApplication(id);
		return ResponseEntity.ok(new MessageResp("Application accepted."));
	}

	@PutMapping("/application/{id}/forward")
	@PreAuthorize(authorities)
	public ResponseEntity<?> forwardApplication(@PathVariable Long id, @RequestBody String locationIdentifier) {
		try {
			applicationService.forwardApplication(id, locationIdentifier);
		} catch (UndefinedLocationException e) {
			return ResponseEntity.badRequest().body(new MessageResp("Location is not found"));
		}
		return ResponseEntity.ok(new MessageResp("Application forwarded"));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize(authorities)
	public void deleteById(@PathVariable Long id) {
		applicationService.deleteById(id);
	}
}
