package com.itechart.retailers.service;

import com.itechart.retailers.model.payload.response.LocationPageResp;
import com.itechart.retailers.model.entity.Location;
import com.itechart.retailers.service.exception.ApplicationNotFoundException;
import com.itechart.retailers.service.exception.CustomerCategoryNotFoundException;
import com.itechart.retailers.service.exception.ItemAmountException;
import com.itechart.retailers.service.exception.TaxesNotDefinedException;

/**
 * The interface Location service.
 */
public interface LocationService {
    /**
     * Gets locations.
     *
     * @param exceptCurrent the except current
     * @param page          the page
     * @return the locations
     */
    LocationPageResp getLocations(Boolean exceptCurrent, Integer page);

    /**
     * Gets current available capacity.
     *
     * @return the current available capacity
     */
    Integer getCurrentAvailableCapacity();

    /**
     * Accept application.
     *
     * @param applicationId the application id
     * @throws TaxesNotDefinedException          the taxes not defined exception
     * @throws ItemAmountException               the item amount exception
     * @throws ApplicationNotFoundException      the application not found exception
     * @throws CustomerCategoryNotFoundException the customer category not found exception
     */
    void acceptApplication(Long applicationId) throws TaxesNotDefinedException, ItemAmountException, ApplicationNotFoundException, CustomerCategoryNotFoundException;
}
