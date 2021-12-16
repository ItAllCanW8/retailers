package com.itechart.retailers.controller;

import com.itechart.retailers.model.entity.Customer;
import com.itechart.retailers.model.entity.User;
import com.itechart.retailers.model.payload.request.CustomerState;
import com.itechart.retailers.model.payload.request.SignUpRequest;
import com.itechart.retailers.model.payload.response.CustomerWithMail;
import com.itechart.retailers.model.payload.response.MessageResponse;
import com.itechart.retailers.service.CustomerService;
import com.itechart.retailers.service.MailService;
import com.itechart.retailers.service.RoleService;
import com.itechart.retailers.service.UserService;
import com.itechart.retailers.util.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/system-admin")
@RequiredArgsConstructor
public class SystemAdminController {

	private final UserService userService;
	private final RoleService roleService;
	private final CustomerService customerService;
	private final MailService emailService;

	private final AuthenticationManager authenticationManager;
	private final PasswordGenerator passwordGenerator;
	private final PasswordEncoder passwordEncoder;

	private final String authorities = "hasAuthority('SYSTEM_ADMIN')";


	@PostMapping
	public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
		if (userService.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken!"));
		}

		Customer customer = customerService.save(Customer.builder()
				.name(signUpRequest.getName())
				.regDate(LocalDate.now())
				.isActive(true)
				.build());

		userService.save(User.builder()
				.name(signUpRequest.getName())
				.email(signUpRequest.getEmail())
				.role(roleService.save("ADMIN"))
				.password(passwordEncoder.encode("1111"))
				.isActive(true)
				.customer(customer)
				.build());

		return ResponseEntity.ok(new MessageResponse("Customer registered successfully!"));
	}

	@PostMapping("{id}")
	@PreAuthorize(authorities)
	public void updateUserStatus(@PathVariable Long id, @RequestBody CustomerState state) {
		userService.changeUserStatus(id, state.isActive());
	}

	@GetMapping
	@PreAuthorize(authorities)
	public List<CustomerWithMail> getCustomers(@RequestParam(required = false) Boolean isOnlyActive) {
		return customerService.findByParams(isOnlyActive).stream()
				.map(customer -> new CustomerWithMail(customer,
						userService.getByRoleAndCustomerId(roleService.getByRole("ADMIN"),
								customer.getId()).getEmail()))
				.toList();
	}
/*
	@PostMapping
	public ResponseEntity<?> createCustomer(@RequestBody SignUpRequest signUpRequest) {
		@Email
		String email = signUpRequest.getEmail();

		if (userService.existsByEmail(email)) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		int lengthOfThePassword = 15;
		String generatedPassword = PasswordGenerator.generatePassword(lengthOfThePassword);

		Role role = roleService.save(Role.builder()
				.role("RETAIL_ADMIN")
				.build());

		User admin = User.builder()
				.name(signUpRequest.getName())
				.email(signUpRequest.getEmail())
				.role(role)
				.password(passwordEncoder.encode(generatedPassword))
				.isActive(true)
				.build();
		admin = userService.save(admin);

		Customer customer = Customer.builder()
				.name(signUpRequest.getName())
				.regDate(LocalDate.now())
				.isActive(true)
				.admin(admin)
				.build();
 		customerService.save(customer);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}*/
}
