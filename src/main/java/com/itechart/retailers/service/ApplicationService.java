package com.itechart.retailers.service;

import com.itechart.retailers.model.entity.Application;
import com.itechart.retailers.model.payload.request.ApplicationReq;

import java.util.List;


public interface ApplicationService {

    List<Application> findAll();

    void save(ApplicationReq applicationDto);

    Application getById(Long id);

    void delete(Application application);

    void deleteById(Long id);
}
