package com.itechart.retailers.repository;

import com.itechart.retailers.model.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findLocationsByCustomerId(Long customerId);

    Optional<Location> findLocationByIdentifier(String identifier);
}
