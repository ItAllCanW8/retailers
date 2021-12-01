package com.itechart.retailers.repository;

import com.itechart.retailers.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Optional<Customer> findByAdminId(Long adminId);

}
