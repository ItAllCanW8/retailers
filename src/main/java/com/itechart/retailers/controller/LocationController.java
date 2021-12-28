package com.itechart.retailers.controller;

import com.itechart.retailers.model.entity.Location;
import com.itechart.retailers.model.payload.response.LocationResp;
import com.itechart.retailers.model.payload.response.MessageResp;
import com.itechart.retailers.repository.LocationRepository;
import com.itechart.retailers.security.service.SecurityContextService;
import com.itechart.retailers.service.AdminService;
import com.itechart.retailers.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LocationController {
	private final AdminService adminService;
	private final SecurityContextService securityService;
	private final LocationService locationService;
	private final LocationRepository locationRepository;
	private final String roles = "hasRole('ADMIN')";

	@GetMapping("/locations")
	@PreAuthorize("hasAuthority('location:get')")
	public List<Location> getLocations() {
		return adminService.findLocations();
	}

	@GetMapping("/current-location")
	@PreAuthorize("hasAuthority('location:get')")
	public LocationResp getCurrentLocation() {
		return new LocationResp(securityService.getCurrentLocation(), locationService.getCurrentAvailableCapacity());
	}

	@GetMapping("/locations-except-current")
	@PreAuthorize("hasAuthority('location:get')")
	public List<Location> getLocationsExceptCurrent() {
		Long currentCustomerId = securityService.getCurrentCustomer().getId();
		Long currentLocationId = securityService.getCurrentLocation().getId();
		return locationRepository.findLocationsByCustomerIdAndIdNot(currentCustomerId, currentLocationId);
	}

	@PostMapping("/locations")
	@PreAuthorize(roles)
	public ResponseEntity<?> createLocation(@RequestBody Location location) {
		adminService.createLocation(location);

		return ResponseEntity.ok(new MessageResp("Location added."));
	}

	@DeleteMapping("/locations/{id}")
	@PreAuthorize(roles)
	public ResponseEntity<?> deleteLocation(@PathVariable Long id) {
		adminService.deleteLocation(id);

		return ResponseEntity.ok(new MessageResp("Location deleted."));
	}

	@DeleteMapping("/locations")
	@PreAuthorize(roles)
	public ResponseEntity<?> deleteLocations(@RequestBody Set<Long> ids) {
		adminService.deleteLocations(ids);

		return ResponseEntity.ok(new MessageResp("Locations deleted."));
	}
}
