package com.itechart.retailers.service;

import com.itechart.retailers.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleService extends JpaRepository<Role, Long> {
}
