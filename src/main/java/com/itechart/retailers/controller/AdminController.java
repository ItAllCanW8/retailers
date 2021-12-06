package com.itechart.retailers.controller;

import com.itechart.retailers.model.entity.Location;
import com.itechart.retailers.model.payload.response.MessageResponse;
import com.itechart.retailers.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

	private final AdminService adminService;
	private final String authorities = "hasAuthority('ADMIN')";
	private Long customerId;

	@GetMapping("/location")
	@PreAuthorize(authorities)
	public List<Location> getLocations() {
		setCustomerIdIfNotSet();

		return adminService.findLocations(customerId);
	}

	@PostMapping("/location")
	@PreAuthorize(authorities)
	public ResponseEntity<?> createLocation(@RequestBody Location location) {
		setCustomerIdIfNotSet();

		adminService.createLocation(customerId, location);

		return ResponseEntity.ok(new MessageResponse("Location added."));
	}

	@DeleteMapping("/location/{id}")
	@PreAuthorize(authorities)
	public ResponseEntity<?> deleteLocation(@PathVariable Long id) {
		adminService.deleteLocation(id);

		return ResponseEntity.ok(new MessageResponse("Location deleted."));
	}

	@DeleteMapping("/location")
	@PreAuthorize(authorities)
	public ResponseEntity<?> deleteLocations(@RequestBody Set<Long> ids) {
		adminService.deleteLocations(ids);

		return ResponseEntity.ok(new MessageResponse("Locations deleted."));
	}

	private void setCustomerIdIfNotSet() {
		if (customerId == null) {
			String adminEmail = SecurityContextHolder.getContext().getAuthentication().getName();
			customerId = adminService.findCustomerId(adminEmail).get();
		}
	}
}
