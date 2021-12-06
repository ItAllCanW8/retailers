package com.itechart.retailers.repository;

import com.itechart.retailers.model.entity.Role;
import com.itechart.retailers.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User getByEmail(String email);

	Boolean existsByEmail(String email);

	List<User> findUserByRole(Role role);

	List<User> findUsersByLocationCustomerAssocCustomerId(Long customerId);

	List<User> findUserByCustomerId(Long id);

	User getByRoleAndCustomerId(Role role, Long customerId);

	Optional<User> findByEmail(String email);

}
