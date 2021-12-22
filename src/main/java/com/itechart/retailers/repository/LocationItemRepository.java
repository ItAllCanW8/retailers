package com.itechart.retailers.repository;

import com.itechart.retailers.model.entity.Location;
import com.itechart.retailers.model.entity.LocationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationItemRepository extends JpaRepository<LocationItem, Long> {

    LocationItem getByItemUpcAndLocation(String upc, Location location);
    List<LocationItem> findByLocation(Location location);
}
