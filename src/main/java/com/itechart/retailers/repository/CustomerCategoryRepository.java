package com.itechart.retailers.repository;

import com.itechart.retailers.model.entity.CustomerCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerCategoryRepository extends JpaRepository<CustomerCategory, Long> {

	Optional<CustomerCategory> findByCustomerIdAndCategoryId(Long customerId, Long categoryId);

	@Modifying
	@Query("update CustomerCategory cc set cc.categoryTax = :newTax where cc.id = :customerCategoryId")
	int updateItemCategoryTax(Long customerCategoryId, Float newTax);

	List<CustomerCategory> findAllByCustomerId(Long customerId);
}
