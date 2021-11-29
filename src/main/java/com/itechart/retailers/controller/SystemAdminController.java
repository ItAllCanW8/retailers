package com.itechart.retailers.controller;

import com.itechart.retailers.model.Customer;
import com.itechart.retailers.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("systemcontrol")
public class SystemAdminController {

	private final CustomerRepository customerRepository;

	@Autowired
	public SystemAdminController(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@GetMapping
	@PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
	public List<Customer> getAll() {
		return customerRepository.findAll();
	}

}
