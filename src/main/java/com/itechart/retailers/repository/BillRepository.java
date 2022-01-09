package com.itechart.retailers.repository;

import com.itechart.retailers.model.entity.Bill;
import com.itechart.retailers.model.entity.projection.BillView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    List<BillView> findAllByLocationId(Long shopId);
}
