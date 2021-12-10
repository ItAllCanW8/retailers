package com.itechart.retailers.controller;

import com.itechart.retailers.model.entity.Location;
import com.itechart.retailers.model.entity.User;
import com.itechart.retailers.model.payload.request.UpdateUserStatusesReq;
import com.itechart.retailers.model.payload.response.MessageResponse;
import com.itechart.retailers.model.entity.projection.UserView;
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

	@GetMapping("/locations")
	@PreAuthorize(authorities)
	public List<Location> getLocations() {
		setCustomerIdIfNotSet();

		return adminService.findLocations(customerId);
	}

	@PostMapping("/locations")
	@PreAuthorize(authorities)
	public ResponseEntity<?> createLocation(@RequestBody Location location) {
		setCustomerIdIfNotSet();
		adminService.createLocation(location, customerId);

		return ResponseEntity.ok(new MessageResponse("Location added."));
	}

	@DeleteMapping("/locations/{id}")
	@PreAuthorize(authorities)
	public ResponseEntity<?> deleteLocation(@PathVariable Long id) {
		adminService.deleteLocation(id);

		return ResponseEntity.ok(new MessageResponse("Location deleted."));
	}

	@DeleteMapping("/locations")
	@PreAuthorize(authorities)
	public ResponseEntity<?> deleteLocations(@RequestBody Set<Long> ids) {
		adminService.deleteLocations(ids);

		return ResponseEntity.ok(new MessageResponse("Locations deleted."));
	}

	@GetMapping("/users")
	@PreAuthorize(authorities)
	public List<UserView> getUsers(){
		setCustomerIdIfNotSet();

		return adminService.findEmployees(customerId);
	}

	@PostMapping("/users")
	@PreAuthorize(authorities)
	public ResponseEntity<?> createUser(@RequestBody User user){
		setCustomerIdIfNotSet();

		adminService.createUser(user, customerId);

		return ResponseEntity.ok(new MessageResponse("User created."));
	}

	@PutMapping("/users/update-statuses")
	@PreAuthorize(authorities)
	public ResponseEntity<?> updateUserStatuses(@RequestBody UpdateUserStatusesReq req){
		adminService.updateUserStatuses(req.getIds(), req.isActive());

		return ResponseEntity.ok(new MessageResponse("Status changed."));
	}

	private void setCustomerIdIfNotSet() {
		if (customerId == null) {
			String adminEmail = SecurityContextHolder.getContext().getAuthentication().getName();
			customerId = adminService.findCustomerId(adminEmail).get();
		}
	}
}
