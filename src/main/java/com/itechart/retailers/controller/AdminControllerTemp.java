package com.itechart.retailers.controller;


import com.itechart.retailers.model.Role;
import com.itechart.retailers.model.User;
import com.itechart.retailers.repository.RoleRepository;
import com.itechart.retailers.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminControllerTemp {


	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	@Autowired
	public AdminControllerTemp(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@GetMapping
	@PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
	public List<User> getAll() {
		String adminRoleName = "admin";
		Role role = roleRepository.findByRole(adminRoleName);
		return userRepository.findUserByRole(role);
	}
}
