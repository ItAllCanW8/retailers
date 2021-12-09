package com.itechart.retailers.repository;

import com.itechart.retailers.model.entity.Role;
import com.itechart.retailers.model.entity.User;
import com.itechart.retailers.repository.projection.UserView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User getByEmail(String email);

	Boolean existsByEmail(String email);

	List<User> findUsersByRole(Role role);

	List<User> findUsersByCustomerId(Long id);

	List<UserView> findUserViewsByCustomerId(Long id);

	User getByRoleAndCustomerId(Role role, Long customerId);

	Optional<User> findByEmail(String email);
}
