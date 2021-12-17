package com.itechart.retailers.repository;

import com.itechart.retailers.model.entity.Role;
import com.itechart.retailers.model.entity.User;
import com.itechart.retailers.model.entity.projection.UserView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> getByEmail(String email);

	Boolean existsByEmail(String email);

	List<User> findUsersByRole(Role role);

	List<User> findUsersByCustomerId(Long id);

	List<UserView> findUserViewsByCustomerId(Long id);

	@Modifying
	@Query("update User u set u.isActive = :newStatus where u.id = :id")
	void changeUserStatus(@Param(value = "id") Long id, @Param(value = "newStatus") boolean newStatus);

	@Query("select u from User u where u.role = ?1 and u.customer.id = ?2")
	User getByRoleAndCustomerId(Role role, Long customerId);

	Optional<User> findByEmail(String email);

	@Query("select u from User u where u.customer.id = ?1 and u.isActive = ?2")
	List<User> findUsersByCustomerIdAndActive(Long customerId, boolean status);

}
