package com.itechart.retailers.service;

import com.itechart.retailers.model.entity.Application;
import com.itechart.retailers.model.entity.Location;
import com.itechart.retailers.model.payload.request.ApplicationReq;
import com.itechart.retailers.model.payload.request.DispatchItemReq;
import com.itechart.retailers.model.payload.response.ApplicationPageResp;
import com.itechart.retailers.service.exception.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The interface Application service.
 */
public interface ApplicationService {

    /**
     * Gets current applications.
     *
     * @param page the page
     * @return the current applications
     */
    ApplicationPageResp getCurrentApplications(Integer page);

    /**
     * Save.
     *
     * @param applicationDto the application dto
     * @throws ItemNotFoundException    the item not found exception
     * @throws ApplicationAlreadyExists the application already exists
     */
    @Transactional
    void save(ApplicationReq applicationDto) throws ItemNotFoundException, ApplicationAlreadyExists;

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     */
    Application getById(Long id);

    /**
     * Delete.
     *
     * @param application the application
     */
    void delete(Application application);

    /**
     * Delete by id.
     *
     * @param id the id
     */
    void deleteById(Long id);

    /**
     * Find applications by dest location list.
     *
     * @param destLocation the dest location
     * @return the list
     */
    List<Application> findApplicationsByDestLocation(Location destLocation);

    /**
     * Gets occupied capacity.
     *
     * @param id the id
     * @return the occupied capacity
     * @throws ApplicationNotFoundException the application not found exception
     */
    Integer getOccupiedCapacity(Long id) throws ApplicationNotFoundException;

    /**
     * Forward application.
     *
     * @param id                 the id
     * @param locationIdentifier the location identifier
     * @throws LocationNotFoundException the location not found exception
     */
    void forwardApplication(Long id, String locationIdentifier) throws LocationNotFoundException;

    /**
     * Dispatch items.
     *
     * @param dispatchItemReq the dispatch item req
     * @throws ItemAmountException       the item amount exception
     * @throws LocationNotFoundException the location not found exception
     * @throws ItemNotFoundException     the item not found exception
     * @throws DispatchItemException     the dispatch item exception
     * @throws ApplicationAlreadyExists  the application already exists
     */
    void dispatchItems(DispatchItemReq dispatchItemReq) throws ItemAmountException, LocationNotFoundException, ItemNotFoundException, DispatchItemException, ApplicationAlreadyExists;

}
