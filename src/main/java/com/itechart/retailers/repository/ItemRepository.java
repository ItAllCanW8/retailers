package com.itechart.retailers.repository;

import com.itechart.retailers.model.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Page<Item> findItemsByCustomerId(Long customerId, Pageable pageable);

    Optional<Item> findItemByUpc(String upc);

//    @Query("select i.id from Item i where i.upc in :upcs")
//    List<Long> findItemIdsByUpc(@Param("upcs") Iterable<String> upcs);

    @Query("select i from Item i where i.upc in :upcs")
    List<Item> findAllByUpc(Iterable<String> upcs);
}
