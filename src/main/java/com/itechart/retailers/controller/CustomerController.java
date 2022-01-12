package com.itechart.retailers.controller;

import com.itechart.retailers.model.entity.Customer;
import com.itechart.retailers.model.entity.User;
import com.itechart.retailers.model.payload.request.SignUpReq;
import com.itechart.retailers.model.payload.response.CustomerPageResp;
import com.itechart.retailers.model.payload.response.MessageResp;
import com.itechart.retailers.service.CustomerService;
import com.itechart.retailers.service.MailService;
import com.itechart.retailers.service.RoleService;
import com.itechart.retailers.service.UserService;
import com.itechart.retailers.service.exception.MailIsAlreadyInUse;
import com.itechart.retailers.util.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;

import static com.itechart.retailers.controller.constant.Message.CUSTOMER_REGISTERED_MSG;
import static com.itechart.retailers.controller.constant.Message.EMAIL_TAKEN_MSG;
import static com.itechart.retailers.security.constant.Authority.SYSTEM_ADMIN_ROLE;

@RestController
@RequestMapping("/api/system-admin")
@RequiredArgsConstructor
public class CustomerController {
	private static final String AUTHORITIES = "hasRole('" + SYSTEM_ADMIN_ROLE + "')";

	private final UserService userService;
	private final CustomerService customerService;

	@PostMapping
	public ResponseEntity<?> registerUser(@RequestBody SignUpReq signUpReq) throws IOException, MailIsAlreadyInUse {
		customerService.registerCustomer(signUpReq.getName(), signUpReq.getEmail());
		return ResponseEntity.ok(new MessageResp(CUSTOMER_REGISTERED_MSG));
	}

	@PostMapping("{id}")
	@PreAuthorize(AUTHORITIES)
	public void updateUserStatus(@PathVariable Long id, @RequestBody Boolean state) {
		userService.changeUserStatus(id, state);
	}

	@GetMapping
	@PreAuthorize(AUTHORITIES)
	public CustomerPageResp getCustomers(
			@RequestParam(required = false) Boolean onlyActive,
			@RequestParam(required = false) Integer page
	) {
		return customerService.findByParams(onlyActive, page);
	}
}
