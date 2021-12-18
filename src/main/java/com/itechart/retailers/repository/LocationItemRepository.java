package com.itechart.retailers.repository;

import com.itechart.retailers.model.entity.LocationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationItemRepository extends JpaRepository<LocationItem, Long> {

}
