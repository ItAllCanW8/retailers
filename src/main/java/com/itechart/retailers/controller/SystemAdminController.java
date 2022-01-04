package com.itechart.retailers.controller;

import com.itechart.retailers.model.entity.Customer;
import com.itechart.retailers.model.entity.User;
import com.itechart.retailers.model.payload.request.SignUpReq;
import com.itechart.retailers.model.payload.response.CustomerResp;
import com.itechart.retailers.model.payload.response.MessageResp;
import com.itechart.retailers.service.CustomerService;
import com.itechart.retailers.service.RoleService;
import com.itechart.retailers.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    private final PasswordEncoder passwordEncoder;

    private final String authorities = "hasRole('SYSTEM_ADMIN')";


    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody SignUpReq signUpReq) {
        if (userService.existsByEmail(signUpReq.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResp("Error: Email is already taken!"));
        }

        Customer customer = customerService.save(Customer.builder()
                .name(signUpReq.getName())
                .regDate(LocalDate.now())
                .isActive(true)
                .build());

        userService.save(User.builder()
                .name(signUpReq.getName())
                .email(signUpReq.getEmail())
                .role(roleService.save("ADMIN"))
                .password(passwordEncoder.encode("1111"))
                .isActive(true)
                .customer(customer)
                .build());

        return ResponseEntity.ok(new MessageResp("Customer registered successfully!"));
    }

    @PostMapping("{id}")
    @PreAuthorize(authorities)
    public void updateUserStatus(@PathVariable Long id, @RequestBody Boolean state) {
        userService.changeUserStatus(id, state);
    }

    @GetMapping
    @PreAuthorize(authorities)
    public List<CustomerResp> getCustomers(@RequestParam(required = false) Boolean isOnlyActive) {
        return customerService.findByParams(isOnlyActive).stream()
                .map(customer -> new CustomerResp(customer,
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
