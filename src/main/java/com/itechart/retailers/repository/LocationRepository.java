package com.itechart.retailers.repository;

import com.itechart.retailers.model.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findLocationsByCustomerId(Long customerId);
    List<Location> findLocationsByCustomerIdAndIdNot(Long customerId, Long id);
    Optional<Location> findLocationByIdentifier(String identifier);
}
