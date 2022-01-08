package com.itechart.retailers.service;

import com.itechart.retailers.model.entity.Location;
import com.itechart.retailers.service.exception.ApplicationNotFoundException;
import com.itechart.retailers.service.exception.CustomerCategoryNotFoundException;
import com.itechart.retailers.service.exception.ItemAmountException;
import com.itechart.retailers.service.exception.TaxesNotDefinedException;

import java.util.List;

public interface LocationService {
    List<Location> getLocations(Boolean exceptCurrent);

    Integer getCurrentAvailableCapacity();

    void acceptApplication(Long applicationId) throws TaxesNotDefinedException, ItemAmountException, ApplicationNotFoundException, CustomerCategoryNotFoundException;
}
