package com.itechart.retailers.repository;

import com.itechart.retailers.model.entity.Customer;
import com.itechart.retailers.model.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findLocationsByCustomer(Customer customer);

    Page<Location> findLocationsByCustomer(Customer customer, Pageable pageable);

    List<Location> findLocationsByCustomerIdAndIdNot(Long customerId, Long id);

    Optional<Location> findLocationByIdentifier(String identifier);

    @Modifying
    @Query("update Location l set l.rentalTaxRate = :newTax where l.id = :locationId")
    int updateRentalTax(@Param(value = "locationId") Long locationId, @Param(value = "newTax") Float newTax);

    Optional<Location> findLocationByIdentifierAndCustomerId(String identifier, Long customerId);

}
