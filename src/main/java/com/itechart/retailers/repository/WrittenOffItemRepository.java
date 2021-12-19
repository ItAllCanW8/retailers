package com.itechart.retailers.repository;

import com.itechart.retailers.model.entity.WrittenOffItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WrittenOffItemRepository extends JpaRepository<WrittenOffItem, Long> {
}
