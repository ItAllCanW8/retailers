package com.itechart.retailers.repository;

import com.itechart.retailers.model.entity.CustomerLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerLocationRepository extends JpaRepository<CustomerLocation, Long> {
}
