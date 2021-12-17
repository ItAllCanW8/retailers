package com.itechart.retailers.repository;

import com.itechart.retailers.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

	List<Item> findItemsByCustomerId(Long customerId);

}
