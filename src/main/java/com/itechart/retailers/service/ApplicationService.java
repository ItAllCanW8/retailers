package com.itechart.retailers.service;

import com.itechart.retailers.model.Application;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ApplicationService {
    List<Application> findAll();

    void save(Application application);

    Application getById(Long id);

    void delete(Application application);

    void deleteById(Long id);
}
