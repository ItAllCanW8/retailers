package com.itechart.retailers.repository;

import com.itechart.retailers.model.entity.LocationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationItemRepository extends JpaRepository<LocationItem, Long> {

    Optional<LocationItem> getByLocationIdAndItemId(Long locationId, Long itemId);

    boolean existsLocationItemByLocationIdAndItemId(Long locationId, Long itemId);
}
