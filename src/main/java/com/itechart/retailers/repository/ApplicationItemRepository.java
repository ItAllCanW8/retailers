package com.itechart.retailers.repository;

import com.itechart.retailers.model.entity.ApplicationItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationItemRepository extends JpaRepository<ApplicationItem, Long> {
}
