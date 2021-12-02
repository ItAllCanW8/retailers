package com.itechart.retailers.service;

import com.itechart.retailers.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    User save(User user);

    User getById(Long id);

    void delete(User user);

    void deleteById(Long id);

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
}
