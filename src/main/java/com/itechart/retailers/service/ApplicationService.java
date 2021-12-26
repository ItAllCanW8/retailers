package com.itechart.retailers.service;

import com.itechart.retailers.model.entity.Application;
import com.itechart.retailers.model.entity.Location;
import com.itechart.retailers.model.payload.request.ApplicationReq;
import com.itechart.retailers.model.payload.request.DispatchItemReq;
import com.itechart.retailers.service.exception.ItemAmountException;
import com.itechart.retailers.service.exception.UndefinedItemException;
import com.itechart.retailers.service.exception.UndefinedLocationException;

import javax.transaction.Transactional;
import java.util.List;


public interface ApplicationService {

    List<Application> getCurrentApplications(Long customerId);

    @Transactional
    void save(ApplicationReq applicationDto, Long customerId) throws UndefinedItemException;

    Application getById(Long id);

    void delete(Application application);

    void deleteById(Long id);

    List<Application> findApplicationsByDestLocation(Location destLocation);

    Integer getOccupiedCapacity(Long id);

    void forwardApplication(Long id, String locationIdentifier) throws UndefinedLocationException;

    void dispatchItems(DispatchItemReq dispatchItemReq, Long customerId) throws ItemAmountException;

}
