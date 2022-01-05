package com.itechart.retailers.repository;

import com.itechart.retailers.model.entity.Application;
import com.itechart.retailers.model.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findApplicationsByDestLocation(Location destLocation);

    List<Application> findApplicationsByCustomerId(Long customerId);

}
