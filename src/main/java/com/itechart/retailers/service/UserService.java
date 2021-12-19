package com.itechart.retailers.service;

import com.itechart.retailers.model.entity.Role;
import com.itechart.retailers.model.entity.User;
import com.itechart.retailers.service.exception.EmptyPasswordException;
import com.itechart.retailers.service.exception.IncorrectPasswordException;

import java.util.List;
import java.util.Optional;

public interface UserService {

	List<User> findAll();

	User save(User user);

	User getById(Long id);

	void update(User user, String currentPassword, String newPassword) throws IncorrectPasswordException, EmptyPasswordException;

	void delete(User user);

	void deleteById(Long id);

	Optional<User> getByEmail(String email);

	Boolean existsByEmail(String email);

	List<User> findUsersByCustomerId(Long customerId);

	User getByRoleAndCustomerId(Role role, Long customerId);

	void changeUserStatus(Long customerId, boolean status);

	List<User> getUsersByRoleNameInCurrentCustomer(String roleName);

}
