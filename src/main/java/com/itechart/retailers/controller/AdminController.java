package com.itechart.retailers.controller;

import com.itechart.retailers.model.entity.Location;
import com.itechart.retailers.model.entity.Supplier;
import com.itechart.retailers.model.entity.User;
import com.itechart.retailers.model.payload.response.MessageResponse;
import com.itechart.retailers.model.entity.projection.UserView;
import com.itechart.retailers.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

	private final PasswordEncoder passwordEncoder;
	private final AdminService adminService;
	private final String authorities = "hasAuthority('ADMIN')";
	private Long customerId;
	private String customerEmail;

	@GetMapping("/locations")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DISPATCHER')")
	public List<Location> getLocations() {
		setCustomerId();

		return adminService.findLocations(customerId);
	}

	@PostMapping("/locations")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DISPATCHER')")
	public ResponseEntity<?> createLocation(@RequestBody Location location) {
		setCustomerId();
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
		setCustomerId();

		return adminService.findEmployees(customerId);
	}

	@PostMapping("/users")
	@PreAuthorize(authorities)
	public ResponseEntity<?> createUser(@RequestBody User user){
		setCustomerId();

		user.setPassword(passwordEncoder.encode("1111"));
		user.setActive(true);
		adminService.createUser(user, customerId);

		return ResponseEntity.ok(new MessageResponse("User created."));
	}

	@PutMapping("/users/{id}")
	@PreAuthorize(authorities)
	public ResponseEntity<?> updateUserStatus(@PathVariable Long id, @RequestBody boolean isActive){
		adminService.updateUserStatus(id, isActive);

		return ResponseEntity.ok(new MessageResponse("Statuses updated."));
	}

	@GetMapping("/suppliers")
	@PreAuthorize(authorities)
	public List<Supplier> getSuppliers(){
		setCustomerId();

		return adminService.findSuppliers(customerId);
	}

	@PostMapping("/suppliers")
	@PreAuthorize(authorities)
	public ResponseEntity<?> createSupplier(@RequestBody Supplier supplier){
		setCustomerId();

		supplier.setActive(true);
		adminService.createSupplier(supplier, customerId);

		return ResponseEntity.ok(new MessageResponse("Supplier created."));
	}

	@PutMapping("/suppliers/{id}")
	@PreAuthorize(authorities)
	public ResponseEntity<?> updateSupplierStatus(@PathVariable Long id, @RequestBody boolean isActive){
		adminService.updateSupplierStatus(id, isActive);

		return ResponseEntity.ok(new MessageResponse("Status updated."));
	}

	private void setCustomerId() {
		String currentCustomerEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!Objects.equals(customerEmail, currentCustomerEmail)) {
			customerEmail = currentCustomerEmail;
			customerId = adminService.findCustomerId(customerEmail).get();
		}
	}
}
