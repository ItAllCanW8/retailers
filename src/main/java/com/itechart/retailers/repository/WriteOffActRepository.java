package com.itechart.retailers.repository;

import com.itechart.retailers.model.entity.WriteOffAct;
import com.itechart.retailers.model.entity.projection.WriteOffActView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WriteOffActRepository extends JpaRepository<WriteOffAct, Long> {

    List<WriteOffActView> findAllByLocationId(Long locationId);

    @Query("select woa from WriteOffAct woa where woa.location.customer.id = :customerId")
    List<WriteOffActView> findAllByCustomerId(@Param("customerId") Long customerId);
}
