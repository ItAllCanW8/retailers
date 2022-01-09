package com.itechart.retailers.repository;

import com.itechart.retailers.model.entity.Application;
import com.itechart.retailers.model.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

	List<Application> findApplicationsByDestLocation(Location destLocation);

    Page<Application> findApplicationsByCustomerIdOrderByIdDesc(Long customerId, Pageable pageable);

    List<Application> findApplicationsByCustomerId(Long customerId);

    Optional<Application> findApplicationByApplicationNumberAndCustomerId(String applicationNumber, Long customerId);

}
