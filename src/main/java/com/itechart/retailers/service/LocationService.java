package com.itechart.retailers.service;

import com.itechart.retailers.model.payload.response.LocationPageResp;
import com.itechart.retailers.model.entity.Location;
import com.itechart.retailers.service.exception.ApplicationNotFoundException;
import com.itechart.retailers.service.exception.CustomerCategoryNotFoundException;
import com.itechart.retailers.service.exception.ItemAmountException;
import com.itechart.retailers.service.exception.TaxesNotDefinedException;

public interface LocationService {
    LocationPageResp getLocations(Boolean exceptCurrent, Integer page);

    Integer getCurrentAvailableCapacity();

    void acceptApplication(Long applicationId) throws TaxesNotDefinedException, ItemAmountException, ApplicationNotFoundException, CustomerCategoryNotFoundException;
}
