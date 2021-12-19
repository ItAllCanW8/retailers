package com.itechart.retailers.repository;

import com.itechart.retailers.model.entity.WriteOffAct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WriteOffActRepository extends JpaRepository<WriteOffAct, Long> {
}
