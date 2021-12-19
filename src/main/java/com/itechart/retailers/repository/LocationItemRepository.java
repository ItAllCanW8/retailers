package com.itechart.retailers.repository;

import com.itechart.retailers.model.entity.LocationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationItemRepository extends JpaRepository<LocationItem, Long> {
//    Integer getAmountByLocation_IdAndItem_Id(Long locationId, Long itemId);
    LocationItem getByLocation_IdAndItem_Id(Long locationId, Long itemId);

    @Modifying
    @Query("update LocationItem li set li.amount = ?1 where li.id = ?2")
    void updateAmountById(Integer newAmount, Long id);
}
