package com.itechart.retailers.service;

import com.itechart.retailers.model.entity.Application;
import com.itechart.retailers.model.entity.Location;
import com.itechart.retailers.model.payload.request.ApplicationReq;
import com.itechart.retailers.model.payload.request.DispatchItemReq;
import com.itechart.retailers.model.payload.response.ApplicationPageResp;
import com.itechart.retailers.service.exception.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ApplicationService {

    ApplicationPageResp getCurrentApplications(Integer page);

    @Transactional
    void save(ApplicationReq applicationDto) throws ItemNotFoundException;

    Application getById(Long id);

    void delete(Application application);

    void deleteById(Long id);

    List<Application> findApplicationsByDestLocation(Location destLocation);

    Integer getOccupiedCapacity(Long id) throws ApplicationNotFoundException;

    void forwardApplication(Long id, String locationIdentifier) throws LocationNotFoundException;

    void dispatchItems(DispatchItemReq dispatchItemReq) throws ItemAmountException, LocationNotFoundException, ItemNotFoundException, DispatchItemException;

}
