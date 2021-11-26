package com.itechart.retailers.repository;

import com.itechart.retailers.model.User_TEST;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User_TEST, Long> {
    Optional<User_TEST> findByEmail(String email);
    Boolean existsByEmail(String email);
}
