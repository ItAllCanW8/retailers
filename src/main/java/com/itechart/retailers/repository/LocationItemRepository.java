package com.itechart.retailers.repository;

import com.itechart.retailers.model.entity.Item;
import com.itechart.retailers.model.entity.Location;
import com.itechart.retailers.model.entity.LocationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationItemRepository extends JpaRepository<LocationItem, Long> {

    LocationItem getByItemUpcAndLocation(String upc, Location location);

    Optional<LocationItem> findLocationItemByItemAndLocation(Item item, Location location);

    List<LocationItem> findByLocation(Location location);

    Optional<LocationItem> getByLocationIdAndItemId(Long locationId, Long itemId);

    @Modifying
    @Query("update LocationItem li set li.amount = :newAmount where li.location.id = :locationId " +
            "and li.item.id = :itemId")
    void updateItemAmount(@Param(value = "locationId") Long locationId, @Param(value = "itemId") Long itemId,
                          @Param(value = "newAmount") Integer newAmount);

    @Query("select li from LocationItem li where li.location.id = :locId and li.item.upc in :itemUpcs")
    List<LocationItem> findAllByLocationIdAndItemUpc(@Param("locId") Long locId, @Param("itemUpcs") Iterable<String> itemUpcs);

    @Query("select li from LocationItem li where li.location.id = :locId and li.item.id in :itemIds")
    List<LocationItem> findAllByLocationIdAndItemId(@Param("locId") Long locId, @Param("itemIds") Iterable<Long> itemIds);
}
