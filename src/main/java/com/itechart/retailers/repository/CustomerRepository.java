package com.itechart.retailers.repository;

import com.itechart.retailers.model.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {


    //Optional<Customer> findByAdminId(Long adminId);

    //Optional<Customer> findByAdminEmail(String email);

    Page<Customer> findByOrderByIdDesc(Pageable pageable);

    Page<Customer> findByIsActiveOrderByIdDesc(Boolean isActive, Pageable pageable);

}
