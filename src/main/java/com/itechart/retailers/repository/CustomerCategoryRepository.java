package com.itechart.retailers.repository;

import com.itechart.retailers.model.entity.CustomerCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerCategoryRepository extends JpaRepository<CustomerCategory, Long> {

	Optional<CustomerCategory> findByCustomerIdAndCategoryId(Long customerId, Long categoryId);

}
