package com.itechart.retailers.repository;

import com.itechart.retailers.model.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    List<Supplier> findByCustomers_Id(Long customerId);

    @Modifying
    @Query("update Supplier s set s.active = :newStatus where s.id = :id")
    void changeSupplierStatus(@Param(value = "id") Long id, @Param(value = "newStatus") boolean newStatus);
}
