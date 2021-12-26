package com.itechart.retailers.repository;

import com.itechart.retailers.model.entity.StateCode;
import com.itechart.retailers.model.entity.StateTax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateTaxRepository extends JpaRepository<StateTax, Long> {

    StateTax getByStateCode(StateCode stateCode);
}
