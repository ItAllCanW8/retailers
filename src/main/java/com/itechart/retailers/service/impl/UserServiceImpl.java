package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.entity.Role;
import com.itechart.retailers.model.entity.User;
import com.itechart.retailers.repository.UserRepository;
import com.itechart.retailers.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public User getById(Long id) {
		return userRepository.getById(id);
	}

	@Override
	public void delete(User user) {
		userRepository.delete(user);
	}

	@Override
	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public User getByEmail(String email) {
		return userRepository.getByEmail(email);
	}

	@Override
	public Boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public List<User> findUsersByLocationCustomerAssocCustomerId(Long customerId) {
//		return userRepository.findUsersByLocationCustomerAssocCustomerId(customerId);
		return null;
	}

	@Override
	public List<User> findUsersByCustomerId(Long customerId) {
		return userRepository.findUserByCustomerId(customerId);
	}

	@Override
	public User getByRoleAndCustomerId(Role role, Long customerId) {
		return userRepository.getByRoleAndCustomerId(role, customerId);
	}
}
