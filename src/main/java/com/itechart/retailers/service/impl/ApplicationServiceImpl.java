package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.Application;
import com.itechart.retailers.repository.ApplicationRepository;
import com.itechart.retailers.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationServiceImpl(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    public List<Application> findAll() {
        return applicationRepository.findAll();
    }

    @Override
    public void save(Application application) {
        applicationRepository.save(application);
    }

    @Override
    public Application getById(Long id) {
        return applicationRepository.getById(id);
    }

    @Override
    public void delete(Application application) {
        applicationRepository.delete(application);
    }

    @Override
    public void deleteById(Long id) {
        applicationRepository.deleteById(id);
    }
}
