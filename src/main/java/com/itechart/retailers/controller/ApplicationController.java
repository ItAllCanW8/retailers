package com.itechart.retailers.controller;

import com.itechart.retailers.model.entity.Application;
import com.itechart.retailers.model.payload.request.ApplicationReq;
import com.itechart.retailers.model.payload.response.MessageResponse;
import com.itechart.retailers.service.ApplicationService;
import com.itechart.retailers.service.exception.UndefinedItemException;
import com.itechart.retailers.service.LocationService;
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
	private final String authorities = "hasAuthority('DISPATCHER') or hasAuthority('WAREHOUSE_MANAGER')" +
			" or hasAuthority('SHOP_MANAGER')";

	@GetMapping("/applications")
	@PreAuthorize(authorities)
	public List<Application> getAll() {
		return applicationService.findAll();
	}

	@GetMapping("/application/{id}")
	@PreAuthorize(authorities)
	public Application getById(@PathVariable Long id) {
		return applicationService.getById(id);
	}

	@PostMapping("/applications")
	@PreAuthorize(authorities)
	public ResponseEntity<?> create(@RequestBody ApplicationReq applicationReq) throws UndefinedItemException {
		try {
			applicationService.save(applicationReq);
		} catch (UndefinedItemException e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Item is not found!"));
		}
		return ResponseEntity.ok(new MessageResponse("Application created."));
	}

	@PutMapping("/application/{id}/accept")
	@PreAuthorize(authorities)
	public ResponseEntity<?> acceptApplication(@PathVariable Long id) {
		if (!locationService.canAcceptApplication(id)) {
			return ResponseEntity.badRequest().body(new MessageResponse("There is no space in location!"));
		}
		locationService.acceptApplication(id);
		return ResponseEntity.ok(new MessageResponse("Application accepted."));
	}

	@PutMapping("/application/{id}/forward")
	@PreAuthorize(authorities)
	public ResponseEntity<?> acceptApplication(@PathVariable Long id, @RequestBody String locationIdentifier) {
		try {
			applicationService.acceptApplication(id, locationIdentifier);
		} catch (UndefinedLocationException e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Location is not found"));
		}
		return ResponseEntity.ok(new MessageResponse("Application forwarded"));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize(authorities)
	public void deleteById(@PathVariable Long id) {
		applicationService.deleteById(id);
	}
}
