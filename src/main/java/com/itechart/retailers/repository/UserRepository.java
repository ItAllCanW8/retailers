package com.itechart.retailers.repository;

import com.itechart.retailers.model.User_TEST;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User_TEST, Long> {
    Optional<User_TEST> findByEmail(String email);
    Boolean existsByEmail(String email);
}
