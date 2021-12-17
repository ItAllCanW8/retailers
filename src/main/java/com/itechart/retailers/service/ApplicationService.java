package com.itechart.retailers.service;

import com.itechart.retailers.model.dto.ApplicationDto;
import com.itechart.retailers.model.entity.Application;
import com.itechart.retailers.model.entity.Location;

import java.util.List;


public interface ApplicationService {

    List<Application> findAll();

    void save(ApplicationDto applicationDto);

    Application getById(Long id);

    void delete(Application application);

    void deleteById(Long id);

    List<Application> findApplicationsByDestLocation(Location destLocation);

}
