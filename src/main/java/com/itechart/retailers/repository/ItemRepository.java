package com.itechart.retailers.repository;

import com.itechart.retailers.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
